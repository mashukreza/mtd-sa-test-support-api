package uk.gov.hmrc.mtdsatestsupportapi.mocks.validators

import api.models.errors.MtdError
import org.scalamock.handlers.CallHandler
import org.scalamock.scalatest.MockFactory
import uk.gov.hmrc.mtdsatestsupportapi.controllers.requestParsers.validators.DeleteStatefulTestDataValidator
import uk.gov.hmrc.mtdsatestsupportapi.helpers.UnitSpec
import uk.gov.hmrc.mtdsatestsupportapi.models.request.deleteStatefulTestData.DeleteStatefulTestDataRawData

class MockDeleteStatefulTestDataValidator extends UnitSpec with MockFactory {

  val mockValidator: DeleteStatefulTestDataValidator = mock[DeleteStatefulTestDataValidator]

  object MockDeleteStatefulTestDataValidator {

    def validate(data: DeleteStatefulTestDataRawData): CallHandler[List[MtdError]] = {
      (mockValidator
        .validate(_: DeleteStatefulTestDataRawData))
        .expects(data)
    }

  }

}
