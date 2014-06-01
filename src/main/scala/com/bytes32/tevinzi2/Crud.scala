package com.bytes32.tevinzi2

/**
 * In memory CRUD, use for testing, not much else
 * extend for database or no-sql
 * @tparam T
 * @tparam Key
 */
trait Crud[T, Key] {

  var store = Map.empty[Key, T]
  var line = List.empty[T]

  def get(key: Key): Option[T] = synchronized {
    store.get(key)
  }

  def remove(key: Key): Unit = synchronized {
    store.get(key).collect {
      case obj =>
        line = line diff List(obj)
    }
    store = store - key
  }

  def save(key: Key, obj: T): Unit = synchronized {
    store = store + ((key, obj))
    line = line :+ obj
  }

  def list(offset: Int, limit: Int, query: Option[String] = None): Iterable[T] = synchronized {
    line.slice(offset, offset + limit)
  }

}
