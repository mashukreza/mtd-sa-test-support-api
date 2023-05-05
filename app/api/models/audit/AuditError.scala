package api.models.audit

import play.api.libs.json.{Json, OFormat}

case class AuditError(errorCode: String)

object AuditError {
  implicit val format: OFormat[AuditError] = Json.format[AuditError]
}
