package uk.gov.hmrc.mtdsatestsupportapi.controllers.requestParsers

import api.models.errors.{ErrorWrapper, VendorClientIdFormatError}
import play.api.libs.json.{JsObject, JsValue, Json}
import uk.gov.hmrc.mtdsatestsupportapi.helpers.UnitSpec
import uk.gov.hmrc.mtdsatestsupportapi.mocks.validators.MockDeleteStatefulTestDataValidator
import uk.gov.hmrc.mtdsatestsupportapi.models.domain.VendorClientId
import uk.gov.hmrc.mtdsatestsupportapi.models.request.deleteStatefulTestData.{DeleteStatefulTestDataRawData, DeleteStatefulTestDataRequest}

class DeleteStatefulTestDataRequestParserSpec extends UnitSpec {

  val vendorClientId = "some_id"
  val requestBody: JsObject = Json.obj("exampleBody" -> "someValue")
  implicit val correlationId: String = "X-123"

  "DeleteStatefulTestDataRequestParser" should {
    val data = DeleteStatefulTestDataRawData(vendorClientId, Some(requestBody))
    "return a request object" when {
      "valid request data is supplied" in new Test {
        MockDeleteStatefulTestDataValidator.validate(data).returns(Nil)

        parser.parseRequest(data) shouldBe Right(DeleteStatefulTestDataRequest(VendorClientId(vendorClientId), Some(requestBody)))
      }
    }
    "return an error" when {
      "a single validation error occurs" in new Test {
        MockDeleteStatefulTestDataValidator.validate(data).returns(List(VendorClientIdFormatError))

        parser.parseRequest(data) shouldBe Left(ErrorWrapper(correlationId, VendorClientIdFormatError, None))
      }
    }
  }

  trait Test extends MockDeleteStatefulTestDataValidator {
    lazy val parser = new DeleteStatefulTestDataRequestParser(mockValidator)
  }


}
