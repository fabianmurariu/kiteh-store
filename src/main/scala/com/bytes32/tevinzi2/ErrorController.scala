package com.bytes32.tevinzi2

import com.twitter.finatra.Controller
import com.bytes32.tevinzi2.Errors.{Unauthorized, NotFound, BadRequest}

trait ErrorController extends Controller{

  error {
    request =>
      request.error match {
        case Some(BadRequest(code)) =>
          render.status(code).toFuture
        case Some(NotFound(code)) =>
          render.status(code).toFuture
        case Some(Unauthorized(_, code)) =>
          render.status(code).toFuture
        case _ =>
          render.status(500).toFuture
      }
  }

}
