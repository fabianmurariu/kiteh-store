package com.bytes32.tevinzi2

import com.typesafe.config.{ConfigFactory, Config}
import scala.util.Try

trait AuthService extends IdGenerator{
  implicit val config: Config = ConfigFactory.load()

  def signOn(where: String): Try[String] = {
    Try(config.getConfig("auth"))
      .flatMap(auth => Try(auth.getConfig(where)))
      .flatMap(
        service => Try {
          val authUri = service.getString("auth_uri")
          val clientId = service.getString("client_id")
          val state = generateId
          val scope = service.getString("scope")
          val redirectUri = service.getString("redirect_uri")
          s"$authUri?client_id=$clientId&state=$state&redirect_uri=$redirectUri&scope=$scope&response_type=code"
        })
  }

}
