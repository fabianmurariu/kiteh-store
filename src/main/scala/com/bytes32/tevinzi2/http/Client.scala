package com.bytes32.tevinzi2.http

import com.twitter.util.Future
import org.apache.http.impl.client.HttpClients
import org.apache.http.client.methods.{HttpGet, CloseableHttpResponse, HttpPost}
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.message.BasicNameValuePair
import com.bytes32.tevinzi2.JavaConverters._
import com.bytes32.tevinzi2.JsonHelpers._
import org.apache.http.util.EntityUtils
import org.apache.commons.io.IOUtils
import java.io.StringWriter

trait Client {

  def post(uri: String, args: Map[String, String]): Future[Map[String, String]] = {
    Future {
      val post = new HttpPost(uri)
      val paramaters = args.map {
        case (name, value) =>
          new BasicNameValuePair(name, value)
      }.asJavaCollection
      post.setEntity(new UrlEncodedFormEntity(paramaters))
      var result: CloseableHttpResponse = null
      try {
        result = HttpClients.createDefault().execute(post)
        val json = result.getEntity.getContent.fromJson()
        val map = json.fields().asScala.map(node => {
          (node.getKey, node.getValue.asText())
        }).toMap
        EntityUtils.consume(result.getEntity)
        map
      } finally {
        if (result != null)
          result.close()
      }
    }
  }



  def get(uri: String, args: Map[String, String]): Future[Map[String, String]] = {
    Future {
      val query: String = args.map {
        case (name, value) => s"$name=$value"
      }.mkString("&")
      val uriAndArgs: String = s"$uri?$query"
      println("REQUEST -> " + uriAndArgs)
      val get = new HttpGet(uriAndArgs)
      var result: CloseableHttpResponse = null
      try {
        result = HttpClients.createDefault().execute(get)
        val writer = new StringWriter()
        IOUtils.copy(result.getEntity.getContent, writer)
        EntityUtils.consume(result.getEntity)
        println("RESPONSE -> "+ writer.toString)
      } finally {
        if (result != null)
          result.close()
      }
      Map()
    }
  }

}