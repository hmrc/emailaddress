val appName = "emailaddress"

lazy val emailaddress = Project(appName, file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)
  .settings(
    scalacOptions ++= Seq(
      "-feature",
      "-language:implicitConversions"
    ),
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.5.12" % Provided,
      "org.scalatest" %% "scalatest" % "2.2.6" % Test,
      "org.pegdown" % "pegdown" % "1.4.2" % Test,
      "org.scalacheck" %% "scalacheck" % "1.12.1" % Test
    ),
    resolvers := Seq(
      Resolver.bintrayRepo("hmrc", "releases"),
      "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
    ),
    crossScalaVersions := Seq("2.11.11", "2.12.3")
  )
