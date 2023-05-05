package api.models.errors

import api.models.audit.AuditError
import play.api.libs.json.{JsObject, Json, Writes}

import scala.collection.Seq

case class ErrorWrapper(correlationId: String, error: MtdError, errors: Option[Seq[MtdError]] = None) {

  private val allErrors: Seq[MtdError] = errors match {
    case Some(seq) => seq
    case None      => Seq(error)
  }

  def auditErrors: Seq[AuditError] =
    allErrors.map(error => AuditError(error.code))

  /** Controller only checks the first/main error code, not the additional errors.
   */
  def containsAnyOf(errorsToCheck: MtdError*): Boolean =
    errorsToCheck.exists(_.code == error.code)
}

object ErrorWrapper {
  implicit val writes: Writes[ErrorWrapper] = (errorResponse: ErrorWrapper) => {

    val json = Json.toJson(errorResponse.error).as[JsObject]

    errorResponse.errors match {
      case Some(errors) if errors.nonEmpty => json + ("errors" -> Json.toJson(errors))
      case _                               => json
    }

  }

}

