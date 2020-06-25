package app.k8ty.sbt.gitlab

import gigahorse.support.okhttp.Gigahorse
import okhttp3.{MediaType, OkHttpClient, Request, RequestBody}
import org.apache.ivy.util.{CopyProgressEvent, CopyProgressListener}
import org.apache.ivy.util.url.IvyAuthenticator
import sbt.{File, URL}
import sbt.internal.librarymanagement.ivyint.{ErrorMessageAuthenticator, GigahorseUrlHandler}

object GitlabURLHandlerHelper {
  lazy val http: OkHttpClient = {
    Gigahorse.http(Gigahorse.config)
      .underlying[OkHttpClient]
      .newBuilder()
      .authenticator(new sbt.internal.librarymanagement.JavaNetAuthenticator)
      .followRedirects(true)
      .followSslRedirects(true)
      .build
  }
}


abstract class GitlabURLHandler extends GigahorseUrlHandler(GitlabURLHandlerHelper.http) {

  val headerName: String
  val headerVal: String

  private val EmptyBuffer: Array[Byte] = new Array[Byte](0)

  override def upload(source: File, dest0: URL, l: CopyProgressListener): Unit = {

    if (("http" != dest0.getProtocol) && ("https" != dest0.getProtocol)) {
      throw new UnsupportedOperationException("URL repository only support HTTP PUT at the moment")
    }

    IvyAuthenticator.install()
    ErrorMessageAuthenticator.install()

    val dest = normalizeToURL(dest0)

    val body = RequestBody.create(MediaType.parse("application/octet-stream"), source)

    val request = new Request.Builder()
      .url(dest)
      .addHeader(headerName, headerVal)
      .put(body)
      .build()

    if (l != null) {
      l.start(new CopyProgressEvent())
    }
    val response = GitlabURLHandlerHelper.http.newCall(request).execute()
    try {
      if (l != null) {
        l.end(new CopyProgressEvent(EmptyBuffer, source.length()))
      }
      validatePutStatusCode(dest, response.code(), response.message())
    } finally {
      response.close()
    }
  }

}

