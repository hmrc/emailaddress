import uk.gov.hmrc.DefaultBuildSettings.targetJvm

enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)

name := "emailaddress"

scalaVersion := "2.12.3"
crossScalaVersions := Seq("2.12.3")
targetJvm := "jvm-1.8"

libraryDependencies ++= AppDependencies()

resolvers := Seq(
  Resolver.bintrayRepo("hmrc", "releases"),
  Resolver.typesafeRepo("releases")
)