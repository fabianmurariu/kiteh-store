package com.bytes32.tevinzi2

import com.twitter.util.Future
import com.bytes32.tevinzi2.data.{ImageSize, Image, Currency, Post}
import org.joda.time.DateTime
import com.bytes32.tevinzi2.Errors.NotFound
import net._01001111.text.LoremIpsum
import scala.math
import scala.util.Random

/**
 * Test class not an actual storage
 */
class Posts extends IdGenerator with MockPostGenerator{

  var postIndex = Map[String, Post]()
  var posts = List[Post]()

  posts ++= 1 to 13 map {
    x =>
      val id = generateId
      val randomPost = makeRandomPost(id)
      postIndex = postIndex + ((id, randomPost))
      randomPost
  }

  def update(postId: String, post: Post): Future[Unit] = {
    Future {
      postIndex.get(postId) match {
        case Some(currentPost@Post(Some(currentId), _, _, _, _, _, _, _, _, _, _)) =>
          val clonePost = currentPost.copy(title = post.title, description = post.description)
          if (postId == currentId) {
            postIndex = postIndex updated(postId, clonePost)
            posts = posts.map(p => if (p == currentPost) clonePost else p)
          }
        case _ => throw new NotFound()
      }
    }
  }

  def getPosts(offset: Int, limit: Int, query: Option[String]): Future[Iterable[Post]] = {
    Future {
      posts.view(offset, offset + limit).filter{
        post =>
          postIndex.contains(post.id.get)
      }
    }
  }

  def addPost(post: Post): Future[String] = {
    Future {
      val id: Some[String] = Some(generateId)
      val newPost = Post(id, post.title, post.description, post.currency, post.price, post.quantity, post.ttl, Some(DateTime.now), Some(DateTime.now))
      postIndex = postIndex updated(id.get, newPost)
      posts = posts :+ newPost
      id.get
    }
  }

  def getPost(id: String): Future[Post] = {
    Future {
      postIndex.get(id) match {
        case Some(post) => post
        case None => throw new NotFound()
      }
    }
  }

  def deletePost(id: String): Future[Unit] = {
    Future {
      if (postIndex.contains(id)) {
        postIndex = postIndex - id
        posts = posts.filter(post => postIndex.contains(id))
      } else throw new NotFound()
    }
  }
}
