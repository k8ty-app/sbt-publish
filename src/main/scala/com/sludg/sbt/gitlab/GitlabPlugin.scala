package com.sludg.sbt.gitlab

import sbt.Keys._
import sbt.{Def, _}
import org.apache.ivy.util.url._


object GitlabPlugin extends AutoPlugin with GitlabKeys {

  object GitlabTokenURLHandler extends GitlabURLHandler {
    override lazy val headerName: String = "Private-Token"
    override lazy val headerVal: String = sys.env.getOrElse(s"GL_PAC_TOKEN", throw new RuntimeException("GL_PAC_TOKEN not set! Aborting"))
  }

  object GitlabCIURLHandler extends GitlabURLHandler {
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
        super.setDownloader("http", GitlabCIURLHandler)
        super.setDownloader("https", GitlabCIURLHandler)
        override def setDownloader(protocol: String, downloader: URLHandler): Unit = {}
      }

      URLHandlerRegistry.setDefault(urlHandlerDispatcher)

      log.info(s"Publishing to ${publishTo.value}")

    },

    configureTokenDispatcher := {
      val log =  streams.value.log
      log.info(s"Updating urlHandlerDispatcher to use GitlabTokenURLHandler")
      val urlHandlerDispatcher = new URLHandlerDispatcher {
        super.setDownloader("http", GitlabTokenURLHandler)
        super.setDownloader("https", GitlabTokenURLHandler)
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
