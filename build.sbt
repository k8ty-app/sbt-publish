sbtPlugin := true

name := "gitlab-plugin"
organization := "app.k8ty.sbt"
version := "0.0.7"

enablePlugins(K8tyGitlabPlugin)
app.k8ty.sbt.gitlab.K8tyGitlabPlugin.gitlabProjectId := "19580632"
