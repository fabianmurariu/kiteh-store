package com.bytes32.tevinzi2
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.bytes32.tevinzi2.data.{Currency, Post}
import org.joda.time.format.{ISODateTimeFormat, DateTimeFormatterBuilder, DateTimeFormatter}
import JsonHelpers._

class JsonHelperTest extends FlatSpec with ShouldMatchers{

  behavior of "JsonHelper"

  it should "convert Post data into JSON and back should be the same" in {
    val formatter = ISODateTimeFormat.dateTime.withZoneUTC
    val dateTime = formatter.parseDateTime("1999-03-12T14:11:22.341Z")
    val expectedPost = Post(Some("1"),"two", "three", created = Some(dateTime))
    expectedPost.toJson should be ("""{"id":"1","title":"two","description":"three","currency":"NONE","price":0,"quantity":1,"ttl":7,"created":"1999-03-12T14:11:22.341Z","updated":null,"tags":[],"images":[]}""")
    val actualPost = """{"id":"1","title":"two","description":"three","currency":"NONE","price":0,"quantity":1,"ttl":7,"created":"1999-03-12T14:11:22.341Z","updated":null,"tags":[],"images":[]}""".fromJson[Post]()
    expectedPost should be (actualPost)
  }
  
}
