package com.bytes32.tevinzi2

import com.twitter.finatra.Controller
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.bytes32.tevinzi2.data.Post
import JsonHelpers._
import com.bytes32.tevinzi2.Errors.{BadRequest, NotFound}
import scala.util.{Failure, Success}

class PostsController(posts: Posts) extends Controller {
  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  get("/") { request =>
    render.static("index.html").toFuture
  }

  get("/posts") {
    request =>
      val offset = request.params.getInt("offset").orElse(Some(0)).get
      val limit = request.params.getInt("limit").orElse(Some(20)).get
      val query = request.params.get("q")
      posts.getPosts(offset, limit, query).map({
        posts =>
          render.status(200).body(posts.toJson).header("Content-Type", "application/json")
      })
  }

  post("/posts") {
    request =>
      request.headers().get("Content-Type") match {
        case "application/json" =>
          val postSafe = request.contentString.fromJsonSafe[Post]()
          postSafe match {
            case Success(post) =>
              posts.addPost(post).map({
                id =>
                  render.status(201).header("location", s"/posts/$id")
              })
            case Failure(_) =>
              throw new BadRequest()
          }
        case _ => throw new BadRequest()
      }
  }

  get("/posts/:id") {
    request =>
      request.routeParams.get("id") match {
        case Some(id) => posts.getPost(id)
          .onFailure(throw _)
          .map {
          post => render.status(200).body(post.toJson).header("Content-Type", "application/json")
        }
        case _ => throw new BadRequest()
      }
  }

  delete("/posts/:id") {
    request =>
      request.routeParams.get("id") match {
        case Some(id) => posts.deletePost(id)
          .onFailure(throw _)
          .map(nothing => render.status(204))
        case _ => throw new BadRequest()
      }
  }

  put("/posts/:id") {
    request =>
      (request.routeParams.get("id"), request.headers().get("Content-Type")) match {
        case (Some(id), "application/json") =>
          val postSafe = request.contentString.fromJsonSafe[Post]()
          postSafe match {
            case Success(post) =>
              posts.update(id, post) map {
                nothing => render.status(204)
              }
            case Failure(_) => throw new BadRequest()
          }
        case _ => throw new BadRequest()
      }
  }

  error {
    request =>
      request.error match {
        case Some(BadRequest(code)) =>
          render.status(code).toFuture
        case Some(NotFound(code)) =>
          render.status(code).toFuture
        case _ =>
          render.status(500).toFuture
      }
  }

}
