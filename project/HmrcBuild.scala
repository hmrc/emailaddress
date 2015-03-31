import sbt._
import Keys._

object HmrcBuild extends Build {

  import uk.gov.hmrc.DefaultBuildSettings
  import DefaultBuildSettings._
  import BuildDependencies._
  import uk.gov.hmrc.{SbtBuildInfo, ShellPrompt}

  val appName = "emailaddress"
  val appVersion = "0.3.0-SNAPSHOT"

  lazy val emailaddress = Project(appName, file("."))
    .settings(version := appVersion)
    .settings(scalaSettings: _*)
    .settings(defaultSettings(): _*)
    .settings(
      targetJvm := "jvm-1.7",
      shellPrompt := ShellPrompt(appVersion),
      libraryDependencies ++= AppDependencies(),
      resolvers := Seq(
        Opts.resolver.sonatypeReleases,
        Opts.resolver.sonatypeSnapshots,
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/",
        "typesafe-snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
      ),
      crossScalaVersions := Seq("2.11.5")
    )
    .settings(SbtBuildInfo(): _*)
    .settings(POMMetadata())

}

private object AppDependencies {

  val compile = Seq.empty

  trait TestDependencies {
    lazy val scope: String = "test"
    lazy val test: Seq[ModuleID] = ???
  }

  object Test {
    def apply() = new TestDependencies {
      override lazy val test = Seq(
        "org.scalatest" %% "scalatest" % "2.2.2" % scope,
        "org.pegdown" % "pegdown" % "1.4.2" % scope,
        "org.scalacheck" %% "scalacheck" % "1.12.1" % scope
      )
    }.test
  }

  def apply() = compile ++ Test()
}


object POMMetadata {

  def apply() = {
    pomExtra :=
      <url>https://www.gov.uk/government/organisations/hm-revenue-customs</url>
      <licenses>
        <license>
          <name>Apache 2</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        </license>
      </licenses>
      <scm>
        <connection>scm:git@github.com:hmrc/emailaddress.git</connection>
        <developerConnection>scm:git@github.com:hmrc/emailaddress.git</developerConnection>
        <url>git@github.com:hmrc/emailaddress.git</url>
      </scm>
      <developers>
        <developer>
          <id>howyp</id>
          <name>Howard Perrin</name>
          <url>http://www.zuhlke.co.uk/</url>
        </developer>
      </developers>
  }
}

