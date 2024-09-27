import sbt.*

object LibDependencies {

  sealed trait PlayVersion

  object PlayVersion {

    case object Play28 extends PlayVersion

    case object Play29 extends PlayVersion

    case object Play30 extends PlayVersion

  }

  def compileDependencies(playVersion: PlayVersion) = playVersion match {
    case PlayVersion.Play29 => "com.typesafe.play" %% "play" % "2.9.0"
    case PlayVersion.Play30 => "org.playframework" %% "play" % "3.0.0"
  }

  val testDependencies = Seq(
    "org.scalatest"       %% "scalatest"       % "3.2.17",
    "org.scalatestplus"   %% "scalacheck-1-17" % "3.2.14.0",
    "org.pegdown"          % "pegdown"         % "1.6.0",
    "org.scalacheck"      %% "scalacheck"      % "1.17.0",
    "com.vladsch.flexmark" % "flexmark-all"    % "0.64.8"
  ).map(_ % Test)

}
