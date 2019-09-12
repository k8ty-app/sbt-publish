GitLab-SBT
==========

This is an SBT plugin to add the appropriate headers required to publish Maven artifacts to GitLab.

This is mostly based on the response from: https://github.com/sbt/sbt/issues/4382#issuecomment-469734888

This is my first SBT plugin, and far from perfect :-)

## Installation

Clone the repository and `sbt publishLocal`

## Usage

### Project Configuration

In your projects `project/plugins.sbt` file, add `addSbtPlugin("com.sludg.sbt" % "gitlab" % "0.5")` 
(or whatever the latest version is)

In your `build.sbt` file, add the following (with your project's appropriate gitlab id)

```
enablePlugins(GitlabPlugin)
com.sludg.sbt.gitlab.GitlabPlugin.gitlabProjectId := "12345"
```

### Execution

To publish with a GitLab PAC token, you will need to have `GL_PAC_TOKEN` set and then run `sbt gitlabTokenPublish`

To publish with a GitLab CI token, you will need to have `CI_JOB_TOKEN` set and then run `sbt gitlabCIPublish`

