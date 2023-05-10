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

package uk.gov.hmrc.mtdsatestsupportapi.connectors

import api.connectors.DownstreamOutcome
import org.scalamock.handlers.CallHandler
import org.scalamock.scalatest.MockFactory
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.mtdsatestsupportapi.models.request.deleteStatefulTestData.DeleteStatefulTestDataRequest

import scala.concurrent.{ExecutionContext, Future}

trait MockDeleteVendorStateConnector extends MockFactory {

  val mockDeleteVendorStateConnector: DeleteVendorStateConnector =
    mock[DeleteVendorStateConnector]

  object MockDeleteVendorStateConnector {

    def deleteVendorState(requestData: DeleteStatefulTestDataRequest): CallHandler[Future[DownstreamOutcome[Unit]]] =
      (mockDeleteVendorStateConnector
        .deleteVendorState(_: DeleteStatefulTestDataRequest)(
          _: HeaderCarrier,
          _: ExecutionContext,
          _: String
        ))
        .expects(requestData, *, *, *)

  }

}
