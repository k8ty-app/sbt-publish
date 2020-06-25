libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value
addSbtPlugin("app.k8ty" % "gitlab-plugin" % "0.0.8")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.3")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "2.0.1")
