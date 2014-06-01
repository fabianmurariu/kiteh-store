package com.bytes32.tevinzi2

import com.bytes32.tevinzi2.Errors.{Unauthorized, NotFound, BadRequest}
import scala.util.{Failure, Success}
import com.twitter.util.{Duration, Future}
import com.twitter.finagle.http.Cookie

class AuthController(auth: AuthService) extends ErrorController {

  get("/signup/:where") {
    request =>
      request.routeParams.get("where") match {
        case Some(where) => auth.signUp(where) match {
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
      //TODO: read who and make sure it matches with auth
      val key = request.getParam("state")
      val code = request.getParam("code")
      val provider = request.routeParams.getOrElse("who", "None")
      if (key != null && code != null) {
        auth.auth(key, code, provider)
          .map(auth => {
          val cookie: Cookie = new Cookie("authKey", auth.id)
          cookie.maxAge = Duration.fromSeconds(Integer.MIN_VALUE) //causes the cookie to expire when the browser is closed
          cookie.httpOnly = false
          cookie.path = "/"
          render.plain("").status(200).cookie(cookie)
        })
          .rescue({
          case failed =>
            render.plain("").status(401).cookie("authKey", "None").toFuture
        })
      } else throw Unauthorized()
  }

}