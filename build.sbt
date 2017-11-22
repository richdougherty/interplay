import buildinfo.BuildInfo._

lazy val interplay = (project in file("."))
  .enablePlugins(PlaySbtPlugin && PlayReleaseBase)
  .settings(playCrossReleasePlugins := false)

description := "Base build plugin for all Play modules"

addSbtPlugin("com.github.gseitz" % "sbt-release" % sbtReleaseVersion)
addSbtPlugin("com.jsuereth" % "sbt-pgp" % sbtPgpVersion)
addSbtPlugin("org.foundweekends" % "sbt-bintray" % sbtBintrayVersion)
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % sbtSonatypeVersion)
addSbtPlugin("com.lightbend" %% "sbt-whitesource" % sbtWhitesourceVersion)

libraryDependencies += {
  val v = sbtVersion.value
  if (v.startsWith("0.")) {
    "org.scala-sbt" % "scripted-plugin" % v,
  } else {
    "org.scala-sbt" %% "scripted-plugin" % v,
  }
}
libraryDependencies += "com.typesafe" % "config" % configVersion

playBuildExtraTests := {
  scripted.toTask("").value
}

playBuildRepoName in ThisBuild := "interplay"

sbtPlugin := true

sbtVersion := "1.0.2"

// I don't think we need to cross-build interplay
//crossSbtVersions := Seq("0.13.16")

scalaVersion := "2.12.4"

addCommandAlias("validate", ";clean;test;scripted")