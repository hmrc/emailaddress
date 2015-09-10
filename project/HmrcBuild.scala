import sbt._
import sbt.Keys._

object HmrcBuild extends Build {

  import uk.gov.hmrc.DefaultBuildSettings._
  import uk.gov.hmrc.SbtAutoBuildPlugin
  import uk.gov.hmrc.versioning.SbtGitVersioning

  val appName = "emailaddress"

  lazy val emailaddress = Project(appName, file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)
    .settings(
      targetJvm := "jvm-1.7",
      libraryDependencies ++= AppDependencies(),
      resolvers := Seq(
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/",
        "Sonatype" at "http://oss.sonatype.org/content/groups/public/",
        Resolver.bintrayRepo("hmrc", "releases")
      ),
      crossScalaVersions := Seq("2.11.7")
    )
}

private object AppDependencies {

  val compile = Seq(
    "com.typesafe.play" %% "play" % "2.3.9" % "provided"
  )

  trait TestDependencies {
    lazy val scope: String = "test"
    lazy val test : Seq[ModuleID] = ???
  }

  object Test {
    def apply() = new TestDependencies {
      override lazy val test = Seq(
        "org.pegdown" % "pegdown" % "1.4.2" % scope,
        "org.scalatest" %% "scalatest" % "2.2.2" % scope,
        "org.scalacheck" %% "scalacheck" % "1.12.1" % scope
      )
    }.test
  }

  def apply() = compile ++ Test()
}
