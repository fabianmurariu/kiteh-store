package com.bytes32.tevinzi2

import com.typesafe.config.{ConfigRenderOptions, ConfigFactory, Config}
import scala.util.{Success, Try}
import JavaConverters._
import com.twitter.util.Future
import com.bytes32.tevinzi2.data.Auth
import com.bytes32.tevinzi2.http.Client
import Errors._

class AuthService(data: Crud[Auth, String], client: Client) extends IdGenerator {
  implicit val config: Config = ConfigFactory.load()

  def signUp(where: String): Try[String] = {
    Try(config.getConfig("auth"))
      .flatMap(auth => Try(auth.getConfig(where)))
      .flatMap(
        service => Try {
          val authUri = service.getString("auth_uri")
          val clientId = service.getString("client_id")
          val state = generateId
          val scope = service.getStringList("scope")
          val joinWith = service.getString("joinWith")
          val redirectUri = service.getString("redirect_uri")
          data.save(state, Auth(state, where, scope.asScala.toList))
          s"$authUri?client_id=$clientId&state=$state&redirect_uri=$redirectUri&scope=${scope.asScala.mkString(joinWith)}&response_type=code"
        })
  }

  def auth(key: String, authCode: String, provider: String): Future[Auth] = {
    val authData = data.get(key).map(a => {
      if (a.provider == provider) {
        a.copy(authorizationCode = Some(authCode))
      } else throw UnauthorizedUnknownProvider()
    })
    authData match {
      case Some(auth) =>
        data.save(auth._id, auth)
        val providerConfig = config.getConfig("auth").getConfig(auth.provider)
        val clientId = providerConfig.getString("client_id")
        val clientSecret = providerConfig.getString("client_secret")
        val redirectUri = providerConfig.getString("redirect_uri")
        val authorizationMethod = providerConfig.getString("authorizationMethod")
        val tokenUri = providerConfig.getString("token_uri")
        val tokenUriParams =
          Try(providerConfig.getConfig("token_uri_params").toMap.map {
            case (k, v) => (k, v.unwrapped().toString)
          }).
            orElse(Success(Map[String, String]())).get

        val params = Map("client_id" -> clientId, "client_secret" -> clientSecret, "redirect_uri" -> redirectUri, "code" -> authCode)
        val authorizationResponse = authorizationMethod match {
          case "GET" => client.get(tokenUri, params ++: tokenUriParams)
          case "POST" => client.post(tokenUri, params ++: tokenUriParams)
          case _ => throw NotFound()
        }
        authorizationResponse.map {
          responseArgs =>
            val ttlKey = providerConfig.getString("ttlKey")
            val codeOption = responseArgs.get("access_token")
            val ttlOption = responseArgs.get(ttlKey)
            (codeOption, ttlOption) match {
              case (Some(code), Some(ttl)) =>
                val successAuth = auth.copy(accessCode = Some(code), ttl = ttl.toInt)
                data.save(successAuth._id, successAuth)
                successAuth
              case _ => throw Unauthorized()
            }
        }
      case None => throw Unauthorized()
    }
  }

  def get(state: String): Option[Auth] = data.get(state)

}
