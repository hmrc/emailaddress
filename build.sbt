val appName = "emailaddress"

lazy val scala212 = "2.12.16"
lazy val scala213 = "2.13.10"
lazy val supportedScalaVersions = Seq(scala213, scala212)

lazy val emailaddress = Project(appName, file("."))
  .settings(majorVersion := 3)
  .settings(scalaVersion := scala213)
  .settings(isPublicArtefact := true)
  .settings(ScoverageSettings.apply(): _*)
  .settings(
    scalacOptions ++= Seq(
      "-feature",
      "-language:implicitConversions"
    ),
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.8.2" % Provided,
      "org.scalatest" %% "scalatest" % "3.2.14" % Test,
      "org.scalatestplus" %% "scalacheck-1-17" % "3.2.14.0" % Test,
      "org.pegdown" % "pegdown" % "1.6.0" % Test,
      "org.scalacheck" %% "scalacheck" % "1.17.0" % Test,
      "com.vladsch.flexmark" % "flexmark-all" % "0.62.2" % Test
    ),
    crossScalaVersions := supportedScalaVersions
  )
