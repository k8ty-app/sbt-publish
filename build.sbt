sbtPlugin := true

name := "gitlab-plugin"
organization := "app.k8ty"
version := "0.0.8"

enablePlugins(K8tyGitlabPlugin)
app.k8ty.sbt.gitlab.K8tyGitlabPlugin.gitlabProjectId := "19580632"
