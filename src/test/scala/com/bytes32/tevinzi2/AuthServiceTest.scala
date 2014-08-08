package com.bytes32.tevinzi2

import org.scalatest.FreeSpec
import org.scalatest.matchers.ShouldMatchers
import com.typesafe.config.ConfigException.Missing
import com.bytes32.tevinzi2.data.Auth
import org.scalatest.mock.MockitoSugar
import com.bytes32.tevinzi2.http.Client
import org.mockito.Mockito._
import com.typesafe.config.ConfigFactory
import com.twitter.util.{Await, Future}
import com.bytes32.tevinzi2.Errors.UnauthorizedUnknownProvider


class AuthServiceTest extends FreeSpec with ShouldMatchers with MockitoSugar {
  val State = ".*state=([a-z0-9A-Z=|_]+).*".r
  val client = mock[Client]
  val srv = new AuthService(new Crud[Auth, String] {}, client)
  val googleAuthConfig = ConfigFactory.load().getConfig("auth").getConfig("google")
  val facebookAuthConfig = ConfigFactory.load().getConfig("auth").getConfig("facebook")

  "AuthService" - {

    "given a request to auth" - {
      "google " in {
        val signUpUrl = srv.signUp("google").get
        signUpUrl match {
          case State(state) =>
            val auth = Auth(state, "google", List("email", "profile"), Some("1"))
            val authorizeUri = "https://accounts.google.com/o/oauth2/token"
            val postArgs =
              Map("code" -> auth.authorizationCode.get,
                "client_id" -> googleAuthConfig.getString("client_id"),
                "client_secret" -> googleAuthConfig.getString("client_secret"),
                "redirect_uri" -> googleAuthConfig.getString("redirect_uri"),
                "grant_type" -> "authorization_code")
            when(client.post(authorizeUri, postArgs)).thenReturn(Future {
              Map("access_token" -> "acc2",
                "refresh_token" -> "rfr2",
                "expires_in" -> "1234",
                "token_type" -> "Bearer")
            })

            val actual = Await.result(srv.auth(key = state, authCode = "1", "google"))
            verify(client).post(authorizeUri, postArgs)
            val expected = auth.copy(accessCode = Some("acc2"), authorizationCode = Some("1"), ttl = 1234)
            actual._id should be(state)
            actual should be(expected)
            srv.get(actual._id) should be(Some(expected))
          case _ => fail("could not find state")
        }
      }

      "facebook" in {
        val signUpUrl = srv.signUp("facebook").get
        signUpUrl match {
          case State(state) =>
            val auth = Auth(state, "facebook", List("email", "public_profile"), Some("1"))
            val authorizeUri = "https://graph.facebook.com/oauth/access_token"
            val getArgs =
              Map("code" -> auth.authorizationCode.get,
                "client_id" -> facebookAuthConfig.getString("client_id"),
                "client_secret" -> facebookAuthConfig.getString("client_secret"),
                "redirect_uri" -> facebookAuthConfig.getString("redirect_uri"))
            when(client.get(authorizeUri, getArgs)).thenReturn(Future {
              Map("access_token" -> "acc2",
                "expires" -> "1234")
            })

            val actual = Await.result(srv.auth(key = state, authCode = "1", "facebook"))
            verify(client).get(authorizeUri, getArgs)
            val expected = auth.copy(accessCode = Some("acc2"), authorizationCode = Some("1"), ttl = 1234)
            actual._id should be(state)
            actual should be(expected)
            srv.get(actual._id) should be(Some(expected))
          case _ => fail("could not find state")
        }
      }

      "Unknown" in {
        val signUpUrl = srv.signUp("facebook").get
        signUpUrl match {
          case State(state) =>
            val auth = Auth(state, "facebook", List("email", "public_profile"), Some("1"))
            val authorizeUri = "https://graph.facebook.com/oauth/access_token"
            val getArgs =
              Map("code" -> auth.authorizationCode.get,
                "client_id" -> facebookAuthConfig.getString("client_id"),
                "client_secret" -> facebookAuthConfig.getString("client_secret"),
                "redirect_uri" -> facebookAuthConfig.getString("redirect_uri"))
            when(client.get(authorizeUri, getArgs)).thenReturn(Future {
              Map("access_token" -> "acc2",
                "expires" -> "1234")
            })

            evaluating {
              Await.result(srv.auth(key = state, authCode = "1", "google"))
            } should produce[UnauthorizedUnknownProvider]
          case _ => fail("could not find state")
        }
      }
    }

    "given a request to singIn " - {
      "google" - {
        "should return a valid oauth URL" in {
          val signUpUrl = srv.signUp("google").get
          signUpUrl should fullyMatch regex
            ("https://accounts.google.com/o/oauth2/auth\\?" +
              "client_id=([0-9\\.a-zA-Z\\-]+)&" +
              "state=([a-z0-9A-Z=|_]+)&" +
              "redirect_uri=http://localhost:7070/auth/google&" +
              "scope=email profile&" +
              "response_type=code").r
          validateState(signUpUrl, "google")
        }
      }
      "facebook" - {
        "should return a valid oauth URL" in {
          val signUpUrl = srv.signUp("facebook").get
          signUpUrl should fullyMatch regex
            ("https://www.facebook.com/dialog/oauth\\?" +
              "client_id=([0-9\\.a-zA-Z]+)&" +
              "state=([a-z0-9A-Z=|_]+)&" +
              "redirect_uri=http://localhost:7070/auth/fb&" +
              "scope=email,public_profile&" +
              "response_type=code").r
          validateState(signUpUrl, "facebook")
        }
      }
      "unknown" - {
        "should return random fail URL" in {
          val signOn = srv.signUp("microsoft")
          signOn.isFailure should be(right = true)
          evaluating {
            throw signOn.failed.get
          } should produce[Missing]
        }
      }
    }
  }

  def validateState(signUpUrl: String, provider: String) {
    signUpUrl match {
      case State(state) =>
        srv.get(state) should not be None
        srv.get(state).get.provider should be(provider)
      case _ => fail("cannot find state")
    }
  }
}
