package com.bytes32.tevinzi2

import org.scalatest.FreeSpec
import org.scalatest.matchers.ShouldMatchers
import scala.util.Failure
import com.bytes32.tevinzi2.Errors.BadRequest
import com.typesafe.config.ConfigException.Missing

class AuthServiceTest extends FreeSpec with ShouldMatchers{

  val srv = new AuthService{}

  "AuthService" - {
    "given a request to singIn " - {
      "google" - {
        "should return a valid oauth URL" in {
          srv.signOn("google").get should fullyMatch regex
            ("https://accounts.google.com/o/oauth2/auth\\?" +
              "client_id=([0-9\\.a-zA-Z]+)&" +
              "state=([a-z0-9A-Z=|_]+)&" +
              "redirect_uri=http://localhost:7070/auth/google&" +
              "scope=email profile&" +
              "response_type=code").r
        }
      }
      "facebook" - {
        "should return a valid oauth URL" in {
          srv.signOn("facebook").get should fullyMatch regex
            ("https://www.facebook.com/dialog/oauth\\?" +
            "client_id=([0-9\\.a-zA-Z]+)&" +
            "state=([a-z0-9A-Z=|_]+)&" +
            "redirect_uri=http://localhost:7070/auth/fb&" +
            "scope=email,public_profile&" +
             "response_type=code").r
        }
      }
      "unknown" - {
        "should return random fail URL" in {
          val signOn = srv.signOn("microsoft")
          signOn.isFailure should be(right = true)
          evaluating{
              throw signOn.failed.get
          } should produce [Missing]
        }
      }
    }
  }

}
