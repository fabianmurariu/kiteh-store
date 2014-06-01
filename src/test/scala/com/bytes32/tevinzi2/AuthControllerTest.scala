package com.bytes32.tevinzi2

import org.scalatest.FreeSpec
import com.twitter.finatra.test.MockApp
import scala.util.Try
import org.scalatest.matchers.ShouldMatchers
import com.typesafe.config.ConfigException.Missing
import com.bytes32.tevinzi2.data.Auth
import org.scalatest.mock.MockitoSugar
import com.bytes32.tevinzi2.http.Client
import org.mockito.Mockito.when
import com.twitter.util.Future

class AuthControllerTest extends FreeSpec with ShouldMatchers with MockitoSugar {

  "Auth" - {
    /* signOn mocked */
    "when a request is made to auth" in {
      val auth = mock[AuthService]
      val app = MockApp(new AuthController(auth))
      when(auth.auth("1", "2", "facebook")).thenReturn(Future {
        Auth("1", "facebook", List(), None, None, 1)
      })
      val result = app.get("/auth/facebook?state=1&code=2")
      result.code should be(200)
      val cookie = result.originalResponse.cookies.get("authKey")
      cookie should not be None
      cookie.get.value should be("1")
      cookie.get.httpOnly should be(false)
      cookie.get.path should be("/")
    }

    "when a request is made to auth but it fails" in {
      val auth = mock[AuthService]
      val app = MockApp(new AuthController(auth))
      when(auth.auth("1", "2", "facebook")).thenReturn(Future {
        throw new RuntimeException
      })
      val result = app.get("/auth/facebook?state=1&code=2")
      result.code should be(401)
      val cookie = result.originalResponse.cookies.get("authKey")
      cookie should not be None
      cookie.get.value should be("None")
    }

    "when a GET request is made to signup" - {
      val app = MockApp(new AuthController(new AuthService(new Crud[Auth, String] {}, mock[Client]) {
        override def signUp(where: String) = Try("uri")
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
        val app = MockApp(new AuthController(new AuthService(new Crud[Auth, String] {}, mock[Client]) {
          override def signUp(where: String) = Try({
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
