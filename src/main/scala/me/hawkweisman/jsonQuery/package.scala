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
  extends AnyVal {
    @inline def apply(idx: Int): Index = new Index(idx, array)
  }

//  object JSONArray {
//
//  }

}
