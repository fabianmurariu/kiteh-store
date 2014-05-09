package com.bytes32.tevinzi2

import com.bytes32.tevinzi2.Errors.{NotFound, BadRequest}
import scala.util.{Failure, Success}

class AuthController(auth: AuthService) extends ErrorController {

  get("/signup/:where") {
    request =>
      request.routeParams.get("where") match {
        case Some(where) => auth.signOn(where) match {
          case Success(location) => {
            println(location)
            render.plain("").status(307).header("Location", location).toFuture
          }
          case Failure(throwable) => throw new NotFound()
        }
        case _ => throw new BadRequest()
      }
  }

  get("/auth/:who") {
    request =>
      println(request)
      render.plain("").status(200).toFuture
  }

}