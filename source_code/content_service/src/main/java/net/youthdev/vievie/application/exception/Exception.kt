package net.youthdev.vievie.application.exception

enum class Exception(
    val errorCode: String,
    val responseCode: String
) {
  FORBIDDEN(
      errorCode = "FORBIDDEN",
      responseCode = "E-1003"
  ),
  INVALID_JWT(
      errorCode = "INVALID_JWT",
      responseCode = "E-1015"
  ),
  JWT_EXPIRED(
      errorCode = "JWT_EXPIRED",
      responseCode = "E-1023"
  ),
  /* common */
  UPLOAD_FOLDER_NOT_EXISTED(
      errorCode = "UPLOAD_FOLDER_NOT_EXISTED",
      responseCode = "E-0001"
  ),
  INCONSISTENT_DATA(
      errorCode = "INCONSISTENT_DATA",
      responseCode = "E-0002"
  ),
  INVALID_SORT_CRITERIA(
      errorCode = "INVALID_SORT_CRITERIA",
      responseCode = "E-0003"
  ),
  NOT_SUPPORTED_OPERATION(
      errorCode = "NOT_SUPPORTED_OPERATION",
      responseCode = "E-0004"
  ),
  INVALID_REQUEST(
      errorCode = "INVALID_REQUEST",
      responseCode = "E-0005"
  ),
  RESOURCE_NOT_EXISTED(
      errorCode = "RESOURCE_NOT_EXISTED",
      responseCode = "E-0006"
  ),
  FIELD_INVALID(
      errorCode = "FIELD_INVALID",
      responseCode = "E-0007"
  )
}