package api

data class ErrorResponse(val result: String)

data class UserNotFound(val result: String, val message: String)

data class EmailNotValid(val result: String)