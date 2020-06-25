package app.k8ty.sbt.gitlab

import sbt.Keys._
import sbt.{Def, _}
import org.apache.ivy.util.url._


object K8tyGitlabPlugin extends AutoPlugin with K8tyGitlabKeys {

  object K8tyGitlabTokenURLHandler$ extends K8tyGitlabURLHandler {
    override lazy val headerName: String = "Private-Token"
    override lazy val headerVal: String = sys.env.getOrElse(s"GL_PAC_TOKEN", throw new RuntimeException("GL_PAC_TOKEN not set! Aborting"))
  }

  object K8tyGitlabCIURLHandler$ extends K8tyGitlabURLHandler {
    override lazy val headerName: String = "Job-Token"
    override lazy val headerVal: String = sys.env.getOrElse(s"CI_JOB_TOKEN", throw new RuntimeException("CI_JOB_TOKEN not set! Aborting"))
  }

  override def projectSettings: Seq[Def.Setting[_]] = Seq(

    publishMavenStyle := true,
    publishTo := Some("GitLab" at s"https://gitlab.com/api/v4/projects/${gitlabProjectId.value}/packages/maven"),
    configureCIDispatcher := {

      val log =  streams.value.log

      log.info(s"Updating urlHandlerDispatcher to use GitlabCIURLHandler")
      val urlHandlerDispatcher = new URLHandlerDispatcher {
        super.setDownloader("http", K8tyGitlabCIURLHandler$)
        super.setDownloader("https", K8tyGitlabCIURLHandler$)
        override def setDownloader(protocol: String, downloader: URLHandler): Unit = {}
      }

      URLHandlerRegistry.setDefault(urlHandlerDispatcher)

      log.info(s"Publishing to ${publishTo.value}")

    },

    configureTokenDispatcher := {
      val log =  streams.value.log
      log.info(s"Updating urlHandlerDispatcher to use GitlabTokenURLHandler")
      val urlHandlerDispatcher = new URLHandlerDispatcher {
        super.setDownloader("http", K8tyGitlabTokenURLHandler$)
        super.setDownloader("https", K8tyGitlabTokenURLHandler$)
        override def setDownloader(protocol: String, downloader: URLHandler): Unit = {}
      }
      URLHandlerRegistry.setDefault(urlHandlerDispatcher)
      log.info(s"Publishing to ${publishTo.value}")
    },

    gitlabTokenPublish := Def.sequential(
      configureTokenDispatcher,
      publish
    ).value,

    gitlabCIPublish := Def.sequential(
      configureCIDispatcher,
      publish
    ).value


  )

}
