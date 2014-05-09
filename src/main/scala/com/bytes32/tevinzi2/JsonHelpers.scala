package com.bytes32.tevinzi2

import com.fasterxml.jackson.databind._
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.datatype.joda.JodaModule
import scala.util.{Success, Try}

object JsonHelpers {

  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
  mapper.registerModule(new JodaModule())

  implicit class TWithJson[T](post: T) {
    def toJson: String = {
      Map[String,String]()
      mapper.writeValueAsString(post)
    }
  }

  implicit class JsonStringToObject(json: String) {
    def fromJson[T]()(implicit m: Manifest[T]): T = mapper.readValue(json)

    /**
     * Returns a Try object and wraps outcome of deserialization
     * @return
     *         Object from JSON
     */
    def fromJsonSafe[T]()(implicit m: Manifest[T]): Try[T] = Try(mapper.readValue(json))
  }

}
