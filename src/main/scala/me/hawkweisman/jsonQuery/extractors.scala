package me.hawkweisman.jsonQuery

import me.hawkweisman.jsonQuery.queries.{Query, Queryable}

/**
  * Created by Eliza on 7/23/16.
  */
object extractors {

  sealed class Json[T: FromJson#Element] {
    @inline def unapply(query: Queryable): Option[T]
      = query.asOption[T]
  }

  object JsonInt extends Json[Int]
  object JsonDouble extends Json[Double]
  object JsonBool extends Json[Boolean]
  object JsonString extends Json[String]

}
