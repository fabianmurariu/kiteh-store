package com.bytes32.tevinzi2.data

case class Auth(id: String, provider: String, scope: Iterable[String],
                authorizationCode: Option[String] = None,
                accessCode: Option[String] = None,
                ttl: Int = -1) {

}