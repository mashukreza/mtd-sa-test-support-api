/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.mtdsatestsupportapi.controllers

import api.controllers.{ControllerBaseSpec, ControllerTestRunner}
import api.mocks.MockIdGenerator
import api.mocks.services.MockEnrolmentsAuthService
import api.models.errors._
import api.models.outcomes.ResponseWrapper
import play.api.http.HeaderNames
import play.api.mvc.Result
import uk.gov.hmrc.mtdsatestsupportapi.mocks.requestParsers.MockStatefulTestDataRequestParser
import uk.gov.hmrc.mtdsatestsupportapi.mocks.services.MockDeleteVendorStateService
import uk.gov.hmrc.mtdsatestsupportapi.models.request.deleteStatefulTestData.{DeleteStatefulTestDataRawData, DeleteStatefulTestDataRequest}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DeleteVendorStateControllerSpec
    extends ControllerBaseSpec
    with ControllerTestRunner
    with MockDeleteVendorStateService
    with MockStatefulTestDataRequestParser
    with MockEnrolmentsAuthService
    with MockIdGenerator {

  trait Test extends ControllerTest {

    val controller = new DeleteVendorStateController(
      cc = cc,
      authService = mockEnrolmentsAuthService,
      parser = mockRequestParser,
      service = mockService,
      idGenerator = mockIdGenerator)

  }

  private val vendorClientId = "some_id"
  override val correlationId = "X-123"

  private val rawData     = DeleteStatefulTestDataRawData(vendorClientId, None)
  private val requestData = DeleteStatefulTestDataRequest(vendorClientId, None)

  "handleRequest" should {
    "return an Ok Action" when {
      "the request received is valid" in new Test {
        override protected def callController(): Future[Result] =
          controller.handleRequest()(fakeDeleteRequestWithHeaders(HeaderNames.AUTHORIZATION -> "Bearer Token", "X-Client-Id" -> "some_id"))

        MockStatefulTestDataRequestParser
          .parseRequest(rawData)
          .returns(Right(requestData))

        MockDeleteVendorStateService
          .deleteVendorState(requestData)
          .returns(Future.successful(Right(ResponseWrapper(correlationId, ()))))

        runOkTest(expectedStatus = NO_CONTENT, maybeExpectedResponseBody = None)
      }
    }
    "return the error as per spec" when {
      "the parser validation fails" in new Test {
        override protected def callController(): Future[Result] =
          controller.handleRequest()(fakeDeleteRequestWithHeaders(HeaderNames.AUTHORIZATION -> "Bearer Token", "X-Client-Id" -> "some_id"))

        MockStatefulTestDataRequestParser
          .parseRequest(rawData)
          .returns(Left(ErrorWrapper(correlationId, NinoFormatError, None)))

        runErrorTest(NinoFormatError)

      }
      "the service returns an error" in new Test {
        override protected def callController(): Future[Result] =
          controller.handleRequest()(fakeDeleteRequestWithHeaders(HeaderNames.AUTHORIZATION -> "Bearer Token", "X-Client-Id" -> "some_id"))

        MockStatefulTestDataRequestParser
          .parseRequest(rawData)
          .returns(Right(requestData))

        MockDeleteVendorStateService
          .deleteVendorState(requestData)
          .returns(Future.successful(Left(ErrorWrapper(correlationId, TaxYearFormatError))))

        runErrorTest(TaxYearFormatError)
      }
    }

    "return an InternalServerError" when {
      "the request is missing an X-Client-Id header" in new Test {
        override protected def callController(): Future[Result] =
          controller.handleRequest()(fakeDeleteRequestWithHeaders(HeaderNames.AUTHORIZATION -> "Bearer Token"))

        val result: Future[Result] = callController()

        status(result) shouldBe InternalError.httpStatus
      }
    }
  }

}
