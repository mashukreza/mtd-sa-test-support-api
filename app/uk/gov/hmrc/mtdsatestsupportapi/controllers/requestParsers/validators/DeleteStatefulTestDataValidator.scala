package uk.gov.hmrc.mtdsatestsupportapi.controllers.requestParsers.validators

import api.controllers.requestParsers.validators.Validator
import api.controllers.requestParsers.validators.validations.VendorClientIdValidation
import api.models.errors.{MtdError, VendorClientIdFormatError}
import uk.gov.hmrc.mtdsatestsupportapi.models.request.deleteStatefulTestData.DeleteStatefulTestDataRawData

class DeleteStatefulTestDataValidator extends Validator[DeleteStatefulTestDataRawData] {

  private val validationSet = List(parameterFormatValidation)

  private def parameterFormatValidation: DeleteStatefulTestDataRawData => List[List[MtdError]] =
    (data: DeleteStatefulTestDataRawData) => List(VendorClientIdValidation.validate(data.vendorClientId))


  override def validate(data: DeleteStatefulTestDataRawData): List[MtdError] = run(validationSet, data).distinct
}
