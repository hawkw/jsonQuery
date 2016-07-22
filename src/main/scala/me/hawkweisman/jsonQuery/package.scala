package me.hawkweisman

import java.math.BigInteger

import scala.reflect.ClassTag
import org.json.{JSONArray, JSONObject}

import scala.language.existentials

/**
  * Created by Eliza on 7/21/16.
  */
package object jsonQuery
extends UnboxedUnion {

  type AnyEnum = E forSome { type E <: java.lang.Enum[E] }

  /**
    * Types we can extract from JSON:
    *  + `org.json`'s  [[JSONArray]] or [[JSONObject]]
    *  + numbers: `Double`, `Int`
    *  + `Boolean` values
    *  + `String`s
    *  + [[java.lang.Enum]]s
    *
    *
    */
  // Todo: extract case classes as well?
  type FromJson
    = ∅ ∨ JSONObject ∨ JSONArray ∨ AnyEnum ∨ Boolean ∨ Double ∨ Int ∨ String ∨
          BigDecimal ∨ BigInteger

  trait Queryable
  extends UnboxedUnion {

    protected[this] def rawOption: Option[Object]

    @inline final def as[T: FromJson#Element : ClassTag]: Option[T]
    = rawOption flatMap {
      case it: T => Some(it)
      case _ => None
    }

    @inline def \ (key: String): Queryable = new Query(key, this)
  }

  class Query(val key: String, private[this] val parent: Queryable)
  extends Queryable {

    override protected[this] lazy val rawOption
      = parent.as[JSONObject] flatMap { parentObj: JSONObject =>
          Option(parentObj opt key)
      }
  }

  class Index(val idx: Int, private[this] val array: JSONArray)
  extends Queryable {

    override protected[this] lazy val rawOption
      = Option(array opt idx)
  }

  implicit class QueryableJsonObject(val obj: JSONObject)
  extends Queryable {
    override protected[this] lazy val rawOption = Option(obj)
  }

  implicit class IndexableJsonArray(val array: JSONArray)
  extends Traversable[Queryable] {
    lazy val length = array.length

    /** Attempt to index the JSON array.
      *
      * The returned [[Index]] can then be rexolved to the desired type
      *
      * @param idx the index to access
      * @return    an [[Index]] object representing the indexing attempt
      * @throws    ArrayIndexOutOfBoundsException if `i < 0` or `length <= i`
      */
    @inline def apply(idx: Int): Index
      = if (idx < 0) throw new ArrayIndexOutOfBoundsException(
          s"index $idx < 0")
        else if (length <= idx) throw new ArrayIndexOutOfBoundsException(
          s"index $idx >= length ($length)")
        else new Index(idx, array)

    override def foreach[U](f: (Queryable) => U): Unit
      = for { i <- 0 until length } f(new Index(i, array))
  }

//  object JSONArray {
//
//  }

}
