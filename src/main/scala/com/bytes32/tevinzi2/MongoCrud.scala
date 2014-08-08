package com.bytes32.tevinzi2

import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._

class MongoCrud[T <: AnyRef, Key](mongo: MongoClient, db: String = "test", collection: String = "collection")
                                 (implicit m: Manifest[T], implicit val writeConcern: WriteConcern = WriteConcern.JournalSafe) extends Crud[T, Key] {

  private val col = new MongoCollection(mongo.getDB(db).getCollection(collection))

  implicit val toDb = (x: T) => {
    grater[T].asDBObject(x)
  }

  implicit val fromDb = (x: DBObject) => {
    grater[T].asObject(x)
  }

  override def get(key: Key): Option[T] = ???

  override def remove(key: Key): Unit = ???

  override def save(key: Key, obj: T): Unit = col.insert(obj)

  override def list(offset: Int, limit: Int, query: Option[String]): Iterable[T] = ???
}