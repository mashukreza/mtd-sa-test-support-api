package uk.gov.hmrc.mtdsatestsupportapi.controllers.requestParsers

import api.controllers.requestParsers.RequestParser
import uk.gov.hmrc.mtdsatestsupportapi.controllers.requestParsers.validators.DeleteStatefulTestDataValidator
import uk.gov.hmrc.mtdsatestsupportapi.models.domain.VendorClientId
import uk.gov.hmrc.mtdsatestsupportapi.models.request.deleteStatefulTestData.{DeleteStatefulTestDataRawData, DeleteStatefulTestDataRequest}

import javax.inject.Inject

class DeleteStatefulTestDataRequestParser @Inject() (val validator: DeleteStatefulTestDataValidator)
  extends RequestParser[DeleteStatefulTestDataRawData, DeleteStatefulTestDataRequest] {

  override protected def requestFor(data: DeleteStatefulTestDataRawData): DeleteStatefulTestDataRequest =
    DeleteStatefulTestDataRequest(VendorClientId(data.vendorClientId), data.body)
}
