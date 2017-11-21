package interplay

import sbt._
import sbt.Keys._
import sbt.io.Path._

object Playdoc extends AutoPlugin {

  final val Docs = ConfigRef("docs")

  object autoImport {
    val playdocDirectory = settingKey[File]("Base directory of play documentation")
    val playdocPackage = taskKey[File]("Package play documentation")
  }

  import autoImport._
  
  override def requires = sbt.plugins.JvmPlugin

  override def trigger = noTrigger

  override def projectSettings =
    Defaults.packageTaskSettings(playdocPackage, mappings in playdocPackage) ++
    Seq(
      playdocDirectory := (baseDirectory in ThisBuild).value / "docs" / "manual",
      mappings in playdocPackage := {
        val base = playdocDirectory.value
        // Use `**(AllPassFilter)` here because it works in both
        // sbt 0.13.16 and 1.0.3. Once we retire support for sbt 0.13.16
        // we can use `allPaths` instead.
        base.allPaths.get.pair(relativeTo(base.getParentFile))
      },
      artifactClassifier in playdocPackage := Some("playdoc"),
      artifact in playdocPackage ~= { _.withConfigurations(Vector(Docs)) }
    ) ++
    addArtifact(artifact in playdocPackage, playdocPackage)

}
