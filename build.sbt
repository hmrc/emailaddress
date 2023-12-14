import LibDependencies.PlayVersion

val appName = "emailaddress"

lazy val scala2_12 = "2.12.16"
lazy val scala2_13 = "2.13.12"

ThisBuild / scalaVersion       := scala2_13
ThisBuild / majorVersion       := 4
ThisBuild / isPublicArtefact   := true

lazy val commonSettings =
  ScoverageSettings() ++
  Seq(
    scalacOptions ++= Seq(
      "-feature",
      "-language:implicitConversions"
    )
  )

lazy val emailaddress = Project(appName, file("."))
  .settings(publish / skip := true)
  .aggregate(
    play28,
    play29,
    play30
  )

lazy val play28 = Project(s"$appName-play-28", file("play-28"))
  .settings(
    crossScalaVersions := Seq(scala2_12, scala2_13),
    libraryDependencies ++= LibDependencies.compileDependencies(PlayVersion.Play28) +: LibDependencies.testDependencies,
    sharedSources
  )
  .settings(commonSettings)


lazy val play29 = Project(s"$appName-play-29", file("play-29"))
  .settings(
    crossScalaVersions := Seq(scala2_13),
    libraryDependencies ++= LibDependencies.compileDependencies(PlayVersion.Play29) +: LibDependencies.testDependencies,
    sharedSources
  )
  .settings(commonSettings)

lazy val play30 = Project(s"$appName-play-30", file("play-30"))
  .settings(
    crossScalaVersions := Seq(scala2_13),
    libraryDependencies ++= LibDependencies.compileDependencies(PlayVersion.Play30) +: LibDependencies.testDependencies,
    sharedSources
  )
  .settings(commonSettings)

def sharedSources = Seq(
  Compile / unmanagedSourceDirectories   += baseDirectory.value / "../shared/src/main/scala",
  Compile / unmanagedResourceDirectories += baseDirectory.value / "../shared/src/main/resources",
  Test    / unmanagedSourceDirectories   += baseDirectory.value / "../shared/src/test/scala",
  Test    / unmanagedResourceDirectories += baseDirectory.value / "../shared/src/test/resources"
)
