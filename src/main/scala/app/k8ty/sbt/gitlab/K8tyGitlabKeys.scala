package app.k8ty.sbt.gitlab

import sbt._

trait K8tyGitlabKeys {

  lazy val gitlabProjectId = settingKey[String]("GitLab Project ID")

  lazy val configureTokenDispatcher = taskKey[Unit]("Configures a URLHandlerDispatcher for use with a Personal Access Token (GL_PAC_Token)")
  lazy val configureCIDispatcher = taskKey[Unit]("Configures a URLHandlerDispatcher for use with a Personal Access Token (CI_JOB_TOKEN)")

  lazy val gitlabCIPublish = taskKey[Unit]("Publish a projects artifacts to it's GitLab Maven endpoint via a CI Job (CI_JOB_TOKEN)")
  lazy val gitlabTokenPublish = taskKey[Unit]("Publish a projects artifacts to it's GitLab Maven endpoint via a Personal Access Token (GL_PAC_Token)")


}
