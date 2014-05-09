package com.bytes32.tevinzi2

import com.twitter.finatra.test.{MockApp, MockResult}
import scala.util.matching.Regex
import org.scalatest.matchers.ShouldMatchers
import com.bytes32.tevinzi2.data.{Currency, Post}
import JsonHelpers._

trait HelperFunc extends ShouldMatchers {

  var result: Option[MockResult] = None

  def verifyHeaderMatchesRegex(result: Option[MockResult], headerName: String, headerRegex: Regex) = {
    result match {
      case Some(response) => response.getHeader(headerName) should fullyMatch regex headerRegex
      case _ => fail("No response available")
    }
  }

  def findPostsAndSetMainResult(offset: Int = 0, limit: Int = 20)(implicit app: MockApp): Unit = {
    result = Some(app.get(s"/posts?offset=$offset&limit=$limit"))
  }

  def createFromJsonAndSetMainResult(path: String, json: String,
                                     headers: Map[String, String] = Map("Content-Type" -> "application/json"))
                                    (implicit app: MockApp) = {
    result = Some(app.post("/posts",
      body = json,
      headers = headers))
  }

  def createSomePosts(howMany: Int)(implicit app: MockApp): Unit = {
    for (x <- 1 to howMany) {
      createFromJsonAndSetMainResult("/posts", Post(title = s"hello $x", description = s"description $x").toJson)
      checkResponseCode(result, 201)
    }
  }

  def returnFromPathAndSetMainResult(path: String,
                                     httpHeaders: Map[String, String] = Map("Accept" -> "application/json"))
                                    (implicit app: MockApp): Unit = {
    result = Some(app.get(path, headers = httpHeaders))
  }

  def checkResponseCode(result: Option[MockResult], code: Int): Unit = {
    result match {
      case Some(response) => response.code should be(code)
      case _ => fail("No response available")
    }
  }

  def parseResponseFromJsonAndVerifyWithExpected(result: Option[MockResult], expected: Post): Unit = {
    result match {
      case Some(response) =>
        response.body should not be ""
        val responseObjectFromJson = response.body.fromJson[Post]()
        responseObjectFromJson.id should not be None
        responseObjectFromJson.created should not be None
        responseObjectFromJson.title should be(expected.title)
        responseObjectFromJson.description should be(expected.description)
      case _ => fail("No response available")
    }
  }

  def deleteFromPathAndSetMainResult(path: String,
                                     httpHeaders: Map[String, String] = Map())
                                    (implicit app: MockApp): Unit = {
    result = Some(app.delete(path, headers = httpHeaders))
  }

  def updatePostAndSetMainResult(previous: Option[MockResult],
                                 post: Post,
                                 headerValues :Map[String,String] = Map("Content-Type" -> "application/json"))
                                (implicit app: MockApp): Unit = {
    previous match {
      case Some(response) =>
        result = Some(app.put(response.getHeader("location"), body = post.toJson, headers = headerValues))
      case _ => fail("No response available")
    }
  }

  def checkSizeAndPosts(expectedSize: Int, expected: Option[Iterable[Post]] = None) {
    result match {
      case Some(response) =>
        val actualPosts = response.body.fromJson[List[Post]]()
        actualPosts.length should be(expectedSize)
        expected match {
          case Some(expectedValue) => actualPosts should be(expectedValue)
          case _ => /* nothing */
        }
      case _ => fail("No result available!")
    }
  }

}
