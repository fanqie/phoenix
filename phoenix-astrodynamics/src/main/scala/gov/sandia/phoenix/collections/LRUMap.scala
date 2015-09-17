package gov.sandia.phoenix.collections

import scala.collection.mutable

/**
 * This is currently only used by DTED, but it prevents an entire library dependency sinc java.util.collections doesn't
 * have an LRU implemented and we don't want all of Apache Commons Collections just for this.
 */
class LRUMap[K, V](val capacity : Int) {
  private var cache = new mutable.LinkedHashMap[K, V]
  def put(key : K, value : V) = cache.synchronized {
    cache+=key->value
    cache.size - capacity match {
      case overflow if overflow > 0 => cache = cache.dropRight(overflow)
      case _ =>
    }
  }

  def size = cache.size
  def get(key : K) = cache.get(key)
  def apply(key : K) = cache(key)
  def containsKey(key : K) = cache.contains(key)
}