sbtPlugin := true

name := "gitlab-plugin"
organization := "app.k8ty"
version := "0.0.8"

homepage := Some(url("https://gitlab.com/k8ty/gitlab-sbt"))
scmInfo := Some(ScmInfo(url("https://gitlab.com/k8ty/gitlab-sbt.git"), "scm:git@gitlab.com:k8ty/gitlab-sbt.git"))
developers := List(Developer("alterationx10", "Mark Rudolph", "mark@k8ty.app", url("https://gitlab.com/alterationx10")))
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
publishMavenStyle := true
sonatypeProfileName := "app.k8ty"

publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

enablePlugins(K8tyGitlabPlugin)
app.k8ty.sbt.gitlab.K8tyGitlabPlugin.gitlabProjectId := "19580632"
