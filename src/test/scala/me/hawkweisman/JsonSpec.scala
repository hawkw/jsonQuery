package me.hawkweisman

import jsonQuery._
import org.json._

import org.scalatest.{WordSpec, Matchers, TryValues}

/**
  * Created by Eliza on 7/22/16.
  */
class JsonSpec
extends WordSpec
  with Matchers
  with TryValues {

  "A simple JSON object" when {
    "querying for a key that exists" should {
      "extract an integer as an int" in {
        val json = new JSONObject("""{"element":1}""")
        (json \ "element").as[Int].success.value shouldEqual 1
      }
      "not extract an integer as a double" in {
        val json = new JSONObject("""{"element":1}""")
        (json \ "element").as[Double] should be a 'failure
      }
     "not extract an integer as an array" in {
       val json = new JSONObject("""{"element":1}""")
        (json \ "element").as[JSONArray] should be a 'failure
      }
      "extract a floating-point number as a double" in {
        val json = new JSONObject("""{"element":1.0}""")
        (json \ "element").as[Double].success.value shouldEqual 1.0
      }
    }
    "querying for an object that does not exist" should {
      "return None" in {
        val json = new JSONObject("""{"element":1}""")
        (json \ "elemnett").as[Double] should be a 'failure
      }
    }
  }

  "A nested JSON object" when {
    "querying a correct path multiple elements deep" should {
      "extract an integer as an Int" in {
        val json = new JSONObject("""{"first": {"second": 1}, "third":3}""")
        (json \ "first" \ "second").as[Int].success.value shouldEqual 1
      }
    }
    "querying a correct path a single element deep" should {
      "extract an integer as an Int" in {
        val json = new JSONObject("""{"first": {"second": 1}, "third":3}""")
        (json \ "third").as[Int].success.value shouldEqual 3
      }
    }
  }

  "A JSON array" when {
    val array = new JSONArray("""[1, 2.0, "hi", {"whatever":"yes"}, true]""")
    "indexed to a position that exists" should {
      "extract an integer element as an Int" in {
        array(0).as[Int].success.value shouldEqual 1
      }
      "extract a decimal element as a Double" in {
        array(1).as[Double].success.value shouldEqual 2.0
      }
      "extract a string element as a String" in {
        array(2).as[String].success.value shouldEqual "hi"
      }
      "extract an object element as a JSONObject" in {
        array(3).as[JSONObject].success.value shouldBe a [JSONObject]
      }
      "allow child objects to be queried correctly" in {
        (array(3) \ "whatever").as[String].success.value shouldEqual "yes"
      }
      "extract a boolean element as a Boolean" in {
        array(4).as[Boolean].success.value shouldEqual true
      }
    }
    "indexed to a position equal to its length" should {
      "throw an exception" in {
        the [ArrayIndexOutOfBoundsException] thrownBy {
          array(5)
        } should have message "index 5 >= length (5)"
      }
    }
    "indexed to a position greater than its length" should {
      "throw an exception" in {
        the [ArrayIndexOutOfBoundsException] thrownBy {
          array(6)
        } should have message "index 6 >= length (5)"
      }
    }
    "indexed to a position less than 0" should {
      "throw an exception" in {
        the [ArrayIndexOutOfBoundsException] thrownBy {
          array(-1)
        } should have message "index -1 < 0"
      }
    }
    "iterated over" should {
      val inOrderArray = new JSONArray("""[1,2,3,4,5,6,7,8,9,10]""")
      "contain the correct elements in the correct sequence" in {
        var i = 0
        inOrderArray foreach { idx =>
          i += 1
          idx.as[Int].success.value shouldEqual i
        }
      }
      "be useable in a for expression generator" in {
        var sum = 0
        for { idx <- inOrderArray
              value <- idx.as[Int] }
          { sum += value }
        sum shouldEqual 55
      }
      "be useable in a for-yield expression" in {
        val result
          = for { idx <- inOrderArray }
            yield { idx.as[Int].success.value + 1 }

        result.toStream should contain inOrder (2,3,4,5,6,7,8,9,10,11)
      }
    }
  }


}
