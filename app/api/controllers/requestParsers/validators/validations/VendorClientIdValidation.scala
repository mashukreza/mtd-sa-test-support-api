package api.controllers.requestParsers.validators.validations

import api.models.errors._
import uk.gov.hmrc.mtdsatestsupportapi.models.domain.VendorClientId

object VendorClientIdValidation {

  private val noValidationErrors = List()

  def validate(id: String): List[MtdError] = if (VendorClientId.isValid(id)) noValidationErrors else List(VendorClientIdFormatError)

}
