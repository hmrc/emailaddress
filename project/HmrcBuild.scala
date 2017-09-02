import sbt.Keys._
import sbt._
import uk.gov.hmrc.DefaultBuildSettings.targetJvm
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning

object HmrcBuild extends Build {

  val appName = "emailaddress"

  lazy val emailaddress = Project(appName, file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)
    .settings(
      libraryDependencies ++= AppDependencies(),
      targetJvm := "jvm-1.8",
      scalaVersion := "2.12.3",
      crossScalaVersions := Seq("2.12.3"),
        resolvers := Seq(
          Resolver.bintrayRepo("hmrc", "releases"),
          Resolver.typesafeRepo("releases")
      )
    )

}

private object AppDependencies {

  val compile = Seq(
    "com.typesafe.play" %% "play-json" % "2.6.3" % "provided"
  )

  trait TestDependencies {
    lazy val scope: String = "test"
    lazy val test: Seq[ModuleID] = ???
  }

  object Test {
    def apply() = new TestDependencies {
      override lazy val test = Seq(
        "org.scalatest" %% "scalatest" % "3.0.3" % scope,
        "org.pegdown" % "pegdown" % "1.6.0" % scope,
        "org.scalacheck" %% "scalacheck" % "1.13.4" % scope
      )
    }.test
  }

  def apply() = compile ++ Test()
}
