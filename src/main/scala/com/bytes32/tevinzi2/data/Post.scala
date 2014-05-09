package com.bytes32.tevinzi2.data

import org.joda.time.DateTime
import com.bytes32.tevinzi2.data.Currency.Currency

case class Post(id: Option[String] = None,
                title: String,
                description: String,
                currency: String = Currency.NONE.toString,
                price: Int = 0, /* always minor currency */
                quantity: Int = 1,
                ttl: Int = 7, /* how long will this last */
                created: Option[DateTime] = None,
                updated: Option[DateTime] = None,
                tags: List[String] = List.empty,
                images: List[Image] = List.empty)

case class Image(id: Option[String] = None,
                 caption: String = "",
                 sizes: List[ImageSize] = List.empty)

case class ImageSize(width: Int, height: Int, url: String)

object Currency extends Enumeration {
  type Currency = Value
  val EUR, RON, GBP, USD, NONE = Value
}