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

package uk.gov.hmrc.mtdsatestsupportapi.models.request.deleteStatefulTestData

import play.api.libs.json.Json
import uk.gov.hmrc.mtdsatestsupportapi.helpers.UnitSpec

class DeleteStatefulTestDataRawDataSpec extends UnitSpec {

  "DeleteStatefulTestDataRawDataSpec" must {
    "write to json" in {
      val body = Json.obj("exampleBody" -> "someValue")
      Json.toJson(DeleteStatefulTestDataRawData("someVendorId", Some(body))) shouldBe
        Json.parse("""
            |{
            | "vendorClientId": "someVendorId",
            | "body": {
            |   "exampleBody": "someValue"
            | }
            |}
            """.stripMargin)
    }
  }

}
