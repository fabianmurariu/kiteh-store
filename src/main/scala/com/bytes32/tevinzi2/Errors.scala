package com.bytes32.tevinzi2

object Errors {

  case class NotFound(code:Int = 404) extends Exception

  case class BadRequest(code:Int = 400) extends Exception

}
