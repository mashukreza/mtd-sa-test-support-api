import play.core.PlayVersion
import play.sbt.PlayImport._
import sbt.Keys.libraryDependencies
import sbt._

object AppDependencies {

  private val bootstrapVersion = "7.15.0"


  val compile = Seq(
    "uk.gov.hmrc" %% "bootstrap-backend-play-28" % bootstrapVersion,
    "org.typelevel" %% "cats-core" % "2.8.0",
  )


  def test(scope: String = "test, it"): Seq[sbt.ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % "3.2.14" % scope,
    "com.vladsch.flexmark" % "flexmark-all" % "0.62.2" % scope,
    "org.scalatestplus" %% "scalacheck-1-15" % "3.2.10.0" % scope,
    "org.scalamock" %% "scalamock" % "5.2.0" % scope,
    "com.typesafe.play" %% "play-test" % PlayVersion.current % scope,
    "com.github.tomakehurst" % "wiremock-jre8" % "2.35.0" % scope,
    "io.swagger.parser.v3" % "swagger-parser-v3" % "2.0.24" % scope
  )
}
