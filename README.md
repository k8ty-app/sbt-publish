k8ty-GitLab-SBT
==========

This is an SBT plugin to add the appropriate headers required to publish Maven artifacts to GitLab.

This is mostly based on the response from: https://github.com/sbt/sbt/issues/4382#issuecomment-469734888

This is my first SBT plugin, and far from perfect :-)

## Installation

This project has been deployed to Maven Central, so by simply adding `addSbtPlugin("app.k8ty" % "gitlab-plugin" % "0.0.8")`
to `project/plugins.sbt`, it should resolve.

If you need to make some changes, clone the repository and `sbt publishLocal` after you do your thing.

## Usage

### Project Configuration

In your projects `project/plugins.sbt` file, add `addSbtPlugin("app.k8ty" % "gitlab-plugin" % "0.0.8")`
(or whatever the latest version is)

In your `build.sbt` file, add the following (with your project's appropriate gitlab id)

```
enablePlugins(K8tyGitlabPlugin)
app.k8ty.sbt.gitlab.K8tyGitlabPlugin.gitlabProjectId := "12345"
```

### Execution

To publish with a GitLab PAC token, you will need to have `GL_PAC_TOKEN` set and then run `sbt k8tyGitlabTokenPublish`

To publish with a GitLab CI token, you will need to have `CI_JOB_TOKEN` set and then run `sbt k8tyGitlabCIPublish`

