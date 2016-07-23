package me.hawkweisman.jsonQuery

import me.hawkweisman.jsonQuery.queries._
import org.json.{JSONArray, JSONObject}

import scala.language.postfixOps
import scala.reflect.ClassTag

/**
  * Created by Eliza on 7/23/16.
  */
object extractors {

  sealed class Json[T: FromJson#Element : ClassTag] {
    @inline def unapply(query: Queryable): Option[T]
      = query.asOption[T]
  }

  object JsonInt extends Json[Int]
  object JsonDouble extends Json[Double]
  object JsonBool extends Json[Boolean]
  object JsonString extends Json[String]

  object JsonArray extends Json[JSONArray] {
    @inline def unapplySeq(query: Queryable): Option[Seq[Index]]
      = query.asOption[JSONArray] map { _ toSeq }
  }

  object JsonObject extends Json[JSONObject] {
    @inline def unapplySeq(query: Queryable): Option[Seq[(String, Query)]]
      = ???
  }

}
