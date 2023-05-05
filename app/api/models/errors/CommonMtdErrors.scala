package api.models.errors

import play.api.http.Status.BAD_REQUEST


object BadRequestError extends MtdError("INVALID_REQUEST", "Invalid request", BAD_REQUEST)

object VendorClientIdFormatError extends MtdError("TO_DO", "TO_DO", BAD_REQUEST)

