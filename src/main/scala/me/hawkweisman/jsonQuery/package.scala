package me.hawkweisman

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
    = ((((((JSONObject \/
      JSONArray) \/ AnyEnum) \/ Boolean) \/ Double) \/ Int) \/
      String)

  trait Queryable extends UnboxedUnion {
//    protected[this] def rawOption: Option[Object]

    @inline final def \ (key: String): Query = new Query(key, this)

    def asJsonObject: Option[JSONObject]
//    @inline final def as[T: FromJson#λ : ClassTag]: Option[T]
//    = rawOption flatMap {
//      case it: T => Some(it)
//      case _ => None
//    }
  }

  class Query(val key: String, private[this] val parent: Queryable)
  extends Queryable with UnboxedUnion {
    //    override protected[this] lazy val rawOption
//      = parent.as[JSONObject] flatMap { parentObj: JSONObject =>
//          Option(parentObj opt key)
//      }
//      = ???
    @inline private[this] def as[T](f: JSONObject => T): Option[T]
      = parent.asJsonObject flatMap { p => Option(f(p)) }

    @inline override def asJsonObject: Option[JSONObject]
      = as(_ optJSONObject key)
    
    @inline def asString: Option[String] = as(_ optString key)
    @inline def asDouble: Option[Double] = as(_ optDouble key)
    @inline def asInt: Option[Int] = as(_ optInt key)
    @inline def asJsonArray: Option[JSONArray] = as(_ optJSONArray key)
  }

  implicit class JsonQueryOps(val obj: JSONObject)
  extends Queryable {
    //    override protected[this] lazy val rawOption = Option(obj)
    @inline override def asJsonObject: Option[JSONObject]
      = Option(obj)
  }

}
