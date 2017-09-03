import sbt._

object AppDependencies {

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
