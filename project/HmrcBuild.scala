import sbt._
import Keys._

object HmrcBuild extends Build {

  import uk.gov.hmrc.DefaultBuildSettings
  import BuildDependencies._

  val nameApp = "emailaddress"
  val versionApp = "0.2.0-SNAPSHOT"

  val appDependencies = Seq(
    play       % "provided",
    scalaTest  % "test",
    pegdown    % "test",
    scalacheck % "test"
  )

  lazy val root = Project(
    id = nameApp,
    base = file("."),
    settings =
      DefaultBuildSettings(appName = nameApp, appVersion = versionApp, scalaversion = "2.11.1", targetJvm = "jvm-1.7")() ++
      Seq(
        crossScalaVersions := Seq("2.11.1", "2.10.4"),
        libraryDependencies ++= appDependencies,
        resolvers := Seq(
          Opts.resolver.sonatypeReleases,
          Opts.resolver.sonatypeSnapshots,
          "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/",
          "typesafe-snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
        )
      ) ++ SonatypeBuild()
  )

}

private object BuildDependencies {

  val scalaTest = "org.scalatest" %% "scalatest" % "2.2.0"
  val pegdown = "org.pegdown" % "pegdown" % "1.4.2" cross CrossVersion.Disabled
  val scalacheck = "org.scalacheck" %% "scalacheck" % "1.11.4"
  val play = "com.typesafe.play" %% "play" % "2.3.0" // TODO should be "provided"?

}

object SonatypeBuild {

  import xerial.sbt.Sonatype._

  def apply() = {
    sonatypeSettings ++ Seq(
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
    )
  }
}

