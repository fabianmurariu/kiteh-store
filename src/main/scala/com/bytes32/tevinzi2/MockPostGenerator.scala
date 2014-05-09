package com.bytes32.tevinzi2

import com.bytes32.tevinzi2.data.{Currency, ImageSize, Image, Post}
import net._01001111.text.LoremIpsum
import scala.util.Random
import org.joda.time.DateTime

trait MockPostGenerator {

  def makeRandomPost(id:String):Post = {
    val ipsum = new LoremIpsum
    val title = ipsum.words(5)
    val description = ipsum.words(15)
    val random = new Random()
    val randomTags = ipsum.words(43).split(" ")
    val randomImages = 1 to 32 map {
      nr =>
        val width = (random.nextInt(5) + 5) * 100
        val height = (random.nextInt(5) + 5) * 100
        Image(Some(s"$id$nr"), ipsum.words(4), List(ImageSize(width, height, s"http://placekitten.com/$width/$height")))
    }
    val tagStart = random.nextInt(randomTags.length - 5)
    val randomImageStart = random.nextInt(randomImages.length - 2)
    Post(Some(id), title, description,
      Currency.RON.toString, random.nextInt(123456), 1, 7,
      Some(DateTime.now()), Some(DateTime.now()),
      randomTags.slice(tagStart, tagStart+4).toList,
      randomImages.slice(randomImageStart, randomImageStart+2).toList
    )
  }

}
