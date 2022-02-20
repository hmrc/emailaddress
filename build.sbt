val appName = "emailaddress"

lazy val emailaddress = Project(appName, file("."))
  .settings(majorVersion := 3)
  .settings(isPublicArtefact := true)
  .settings(
    scalacOptions ++= Seq(
      "-feature",
      "-language:implicitConversions"
    ),
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.6.13" % Provided,
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      "org.pegdown" % "pegdown" % "1.6.0" % Test,
      "org.scalacheck" %% "scalacheck" % "1.13.5" % Test
    ),
    crossScalaVersions := Seq("2.11.11", "2.12.3")
  )
