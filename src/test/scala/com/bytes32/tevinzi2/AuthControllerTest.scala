package com.bytes32.tevinzi2

import org.scalatest.FreeSpec
import com.twitter.finatra.test.MockApp
import scala.util.Try
import org.scalatest.matchers.ShouldMatchers
import com.typesafe.config.ConfigException.Missing

class AuthControllerTest extends FreeSpec with ShouldMatchers {

  "Auth" - {
    /* signOn mocked */

    "when a GET request is made to auth" - {
      val app = MockApp(new AuthController(new AuthService {
        override def signOn(where: String) = Try("uri")
      }))
      "supported" - {
        "facebook" in {
          val result = app.get("/signup/facebook")
          result.code should be(307)
          result.getHeader("Location") should be("uri")
        }
        "google" in {
          val result = app.get("/signup/google")
          result.code should be(307)
          result.getHeader("Location") should be("uri")
        }
      }

      "not supported" - {
        val app = MockApp(new AuthController(new AuthService {
          override def signOn(where: String) = Try({
            throw new Missing("somwhere")
          })
        }))

        "yahoo" in {
          val result = app.get("/signup/yahoo")
          result.code should be(404)
        }
        "twitter" in {
          val result = app.get("/signup/twitter")
          result.code should be(404)
        }

      }
    }
  }

}
