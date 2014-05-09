package com.bytes32.tevinzi2.data

case class User(id:String)

object Login extends Enumeration {
  type Login = Value
  val FACEBOOK, GOOGLE = Value
}