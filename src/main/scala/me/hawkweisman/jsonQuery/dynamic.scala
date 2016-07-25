package me.hawkweisman.jsonQuery

import me.hawkweisman.jsonQuery.queries.{Query, Queryable}

import scala.language.dynamics
/**
  * Created by Eliza on 7/25/16.
  */
object dynamic {

  implicit class DynamicJson(val json: Queryable)
  extends Dynamic {

    @inline final def selectDynamic(key: String): Queryable
      = json \ key

    def updateDynamic[T: FromJson#Element](key: String)(value: T): Unit
      = ???


  }

}
