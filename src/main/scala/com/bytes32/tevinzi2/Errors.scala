package com.bytes32.tevinzi2

object Errors {

  case class NotFound(code: Int = 404) extends Exception

  case class BadRequest(code: Int = 400) extends Exception

  case class Unauthorized(cause: Throwable = null, code: Int = 401) extends Exception(cause)

  case class UnauthorizedUnknownProvider(code:Int = 401) extends Exception

}
