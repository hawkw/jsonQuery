package me.hawkweisman.jsonQuery

import org.json.{JSONArray, JSONObject}

import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}

/**
  * Created by Eliza on 7/23/16.
  */
object queries extends UnboxedUnion {
  trait Queryable
    extends UnboxedUnion {

    protected[this] def rawOption: Option[Object]
    protected[this] def rawTry: Try[Object]

    @inline final def as[T: FromJson#Element : ClassTag]: Try[T]
    = rawTry flatMap {
      case it: T => Success(it)
      case it => Failure(
        // TODO: make this error better
        new Exception(s"Could not represent $it as requested type"))
    }

    @inline final def asOption[T: FromJson#Element : ClassTag]: Option[T]
    = rawOption flatMap {
      case it: T => Some(it)
      case _ => None
    }

    @inline def \ (key: String): Queryable = new Query(key, this)
  }

  class Query(val key: String, private[this] val parent: Queryable)
    extends Queryable {

    override protected[this] lazy val rawOption
    = parent.asOption[JSONObject] flatMap { parentObj: JSONObject =>
      Option(parentObj opt key)
    }

    override protected[this] lazy val rawTry
    = parent.as[JSONObject] flatMap { parentObj: JSONObject =>
      Try(parentObj get key)
    }
  }

  class Index(val idx: Int, private[this] val array: JSONArray)
    extends Queryable {

    override protected[this] lazy val rawOption
    = Option(array opt idx)

    override protected[this] lazy val rawTry
    = Try(array get idx)
  }

  implicit class QueryableJsonObject(val obj: JSONObject)
    extends Queryable {
    override protected[this] lazy val rawOption = Option(obj)
    override protected[this] lazy val rawTry = Success(obj)
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
    = idx match {
      case _ if idx < 0 =>
        throw new ArrayIndexOutOfBoundsException(s"index $idx < 0")
      case _ if length <= idx =>
        throw new ArrayIndexOutOfBoundsException(
          s"index $idx >= length ($length)")
      case _ => new Index(idx, array)
    }

    override def foreach[U](f: (Queryable) => U): Unit
    = for { i <- 0 until length } f(new Index(i, array))
  }

}
