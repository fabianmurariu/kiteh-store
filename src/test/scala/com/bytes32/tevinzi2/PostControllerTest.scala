package com.bytes32.tevinzi2

import org.scalatest.FreeSpec
import com.twitter.finatra.test.{MockResult, MockApp}
import JsonHelpers._
import com.bytes32.tevinzi2.data.Post

class PostControllerTest extends FreeSpec with HelperFunc {
  val original = Post(title = "this is a new post", description = "hello awesome post")
  var location = ""

  "API" - {
    "create new post and return it" - {
      implicit val app = MockApp(new PostsController(new Posts))
      "WTF" in {app.get("/posts")}
      "when a new post is created"                      in createFromJsonAndSetMainResult("/posts", original.toJson)
      "the response code should be 201"                 in checkResponseCode(result, 201)
      "the location header should be /posts/:id"        in verifyHeaderMatchesRegex(result, "location", "/posts/[a-z0-9A-Z=|_]+".r)
      "when we call GET on the location header value"   in returnFromPathAndSetMainResult(result.get.getHeader("location"))
      "the response code should be 200"                 in checkResponseCode(result, 200)
      "the Content-Type should be application/json"     in verifyHeaderMatchesRegex(result, "Content-Type", "application/json".r)
      "the response body should match the original"     in parseResponseFromJsonAndVerifyWithExpected(result, original)
    }

    "create a new post, fail on content-type" - {
      implicit val app = MockApp(new PostsController(new Posts))
      "new post request no content-type"                in createFromJsonAndSetMainResult("/posts", original.toJson, Map())
      "the response code should be 400"                 in checkResponseCode(result, 400)
    }

    "update a post, fail on content-type" - {
      implicit val app = MockApp(new PostsController(new Posts))
      "when a new post is created"                      in createFromJsonAndSetMainResult("/posts", original.toJson)
      "the response code should be 201"                 in checkResponseCode(result, 201)
      "the location header should be /posts/:id"        in verifyHeaderMatchesRegex(result, "location", "/posts/[a-z0-9A-Z=|_]+".r)
      "update Post with a new value, no Content-Type"   in {
                                                          location = result.get.getHeader("location")
                                                          updatePostAndSetMainResult(result, original.copy(title=" my title my post"), Map())
                                                        }
      "the response code should be 400"                 in checkResponseCode(result, 400)
    }

    "create a new post and delete it" - {
      implicit val app = MockApp(new PostsController(new Posts))
      "when a new post is created"                      in createFromJsonAndSetMainResult("/posts", original.toJson)
      "the response code should be 201"                 in checkResponseCode(result, 201)
      "when we call GET on the location header"         in {location = result.get.getHeader("location"); returnFromPathAndSetMainResult(location)}
      "the response code should be 200"                 in checkResponseCode(result, 200)
      "the Content-Type should be application/json"     in verifyHeaderMatchesRegex(result, "Content-Type", "application/json".r)
      "when we call DELETE on the location header"      in deleteFromPathAndSetMainResult(location)
      "the response code should be 204"                 in checkResponseCode(result, 204)
      "when we call GET on the object location"         in returnFromPathAndSetMainResult(location)
      "the response code should be 404"                 in checkResponseCode(result, 404)
    }

    "create a new post and update it" - {
      implicit val app = MockApp(new PostsController(new Posts))
      "when a new post is created"                      in createFromJsonAndSetMainResult("/posts", original.toJson)
      "the response code should be 201"                 in checkResponseCode(result, 201)
      "the location header should be /posts/:id"        in verifyHeaderMatchesRegex(result, "location", "/posts/[a-z0-9A-Z=|_]+".r)
      "update Post with a new value"                    in {
                                                            location = result.get.getHeader("location")
                                                            updatePostAndSetMainResult(result, original.copy(title=" my title my post"))
                                                        }
      "the response code should be 204"                 in checkResponseCode(result, 204)
      "when we call GET on the location header value"   in returnFromPathAndSetMainResult(location)
      "the response body should match the update"       in parseResponseFromJsonAndVerifyWithExpected(result, original.copy(title=" my title my post"))
    }

    "find /posts offset limit" - {
      implicit val app = MockApp(new PostsController(new Posts))
      var allPosts:List[Post] = null
      "offset 0 no limit" - {
        "create 3 posts"                                in createSomePosts(3)
        "find all posts"                                in findPostsAndSetMainResult()
        "the response code should be 200"               in checkResponseCode(result, 200)
        "the Content-Type should be application/json"   in verifyHeaderMatchesRegex(result, "Content-Type", "application/json".r)
        "the size of the list is 3"                     in {checkSizeAndPosts(3); allPosts = result.get.body.fromJson[List[Post]]()}
      }
      "offset 1 limit 2" - {
        "find 2 posts"                                  in findPostsAndSetMainResult(offset = 1, limit = 2)
        "the response code should be 200"               in checkResponseCode(result, 200)
        "the Content-Type should be application/json"   in verifyHeaderMatchesRegex(result, "Content-Type", "application/json".r)
        "the list should have 2 elements"               in checkSizeAndPosts(2, Some(allPosts.view(1,3).toList))
      }
      "offset 2 limit 2" - {
        "find 1 posts"                                  in findPostsAndSetMainResult(offset = 2, limit = 2)
        "the response code should be 200"               in checkResponseCode(result, 200)
        "the Content-Type should be application/json"   in verifyHeaderMatchesRegex(result, "Content-Type", "application/json".r)
        "the list should have 2 elements"               in checkSizeAndPosts(1, Some(allPosts.view(2,4).toList))
      }
    }

    "HTTP call on unknown post" -{
      implicit val app = MockApp(new PostsController(new Posts))
      "GET" - {
        "/posts/unknown"                                in returnFromPathAndSetMainResult("/posts/unknown")
        "the response code should be 404"               in checkResponseCode(result, 404)
      }
      "DELETE no id" - {
        "/posts"                                        in deleteFromPathAndSetMainResult("/posts")
        "the response code should be 404"               in checkResponseCode(result, 404)
      }
      "DELETE" - {
        "/posts/unknown"                                in deleteFromPathAndSetMainResult("/posts/unknown")
        "the response code should be 404"               in checkResponseCode(result, 404)
      }
      "PUT nothing" - {
        "/posts/unknown"                                in {result = Some(app.put("/posts/unknown"))}
        "the response code should be 400"               in checkResponseCode(result, 400)
      }

      "PUT no content-type" - {
        "/posts/unknown"                                in {result = Some(app.put("/posts/unknown", body = original.toJson))}
        "the response code should be 400"               in checkResponseCode(result, 400)
      }

      "PUT not found" - {
        "/posts/unknown"                                in {result = Some(app.put("/posts/unknown",
                                                                                  body = original.toJson,
                                                                                  headers = Map("Content-Type" ->"application/json")))}
        "the response code should be 404"               in checkResponseCode(result, 404)
      }
      "POST nothing" - {
        "/posts/unknown"                                in {result = Some(app.post("/posts/unknown"))}
        "the response code should be 404"               in checkResponseCode(result, 404)
      }
      "POST no content-type" - {
        "/posts/unknown"                                in {result = Some(app.post("/posts/unknown", body = original.toJson))}
        "the response code should be 404"               in checkResponseCode(result, 404)
      }
    }
  }
}
