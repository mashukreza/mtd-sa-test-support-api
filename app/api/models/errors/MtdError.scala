package api.models.errors

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsObject, JsPath, Json, OWrites}

import scala.collection.Seq

case class MtdError(code: String, message: String, httpStatus: Int, paths: Option[Seq[String]] = None) {
  val asJson: JsObject = Json.toJson(this).as[JsObject]

  def withExtraPath(newPath: String): MtdError = paths.fold(this.copy(paths = Some(Seq(newPath)))) { existingPaths =>
    this.copy(paths = Some(existingPaths :+ newPath))
  }

}

object MtdError {

  implicit val writes: OWrites[MtdError] = (
    (JsPath \ "code").write[String] and
      (JsPath \ "message").write[String] and
      (JsPath \ "paths").writeNullable[Seq[String]]
    )(unlift(MtdError.unapply))

  // excludes httpStatus
  def unapply(e: MtdError): Option[(String, String, Option[Seq[String]])] = Some((e.code, e.message, e.paths))

  implicit def genericWrites[T <: MtdError]: OWrites[T] =
    writes.contramap[T](c => c: MtdError)

}

object CustomMtdError {
  def unapply(arg: MtdError): Option[String] = Some(arg.code)
}
