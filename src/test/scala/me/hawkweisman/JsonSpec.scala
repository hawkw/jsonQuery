package me.hawkweisman

import org.scalatest.{FlatSpec, Matchers, OptionValues, WordSpec}

/**
  * Created by Eliza on 7/22/16.
  */
class JsonSpec
extends WordSpec
  with Matchers
  with OptionValues {

  import jsonQuery._
  import org.json._

  "A simple JSON object" when {
    "querying for a key that exists" should {
      "extract an integer as an int" in {
        val json = new JSONObject("""{"element":1}""")
        (json \ "element").as[Int].value shouldEqual 1
      }
      "not extract an integer as a double" in {
        val json = new JSONObject("""{"element":1}""")
        (json \ "element").as[Double] should not be 'defined
      }
     "not extract an integer as an array" in {
       val json = new JSONObject("""{"element":1}""")
        (json \ "element").as[JSONArray] should not be 'defined
      }
      "extract a floating-point number as a double" in {
        val json = new JSONObject("""{"element":1.0}""")
        (json \ "element").as[Double].value shouldEqual 1.0
      }
    }
    "querying for an object that does not exist" should {
      "return None" in {
        val json = new JSONObject("""{"element":1}""")
        (json \ "elemnett").as[Double] should not be 'defined
      }
    }
  }

  "A nested JSON object" when {
    "querying a correct path multiple elements deep" should {
      "extract an integer as an Int" in {
        val json = new JSONObject("""{"first": {"second": 1}, "third":3}""")
        (json \ "first" \ "second").as[Int].value shouldEqual 1
      }
    }
    "querying a correct path a single element deep" should {
      "extract an integer as an Int" in {
        val json = new JSONObject("""{"first": {"second": 1}, "third":3}""")
        (json \ "third").as[Int].value shouldEqual 3
      }
    }
  }


}
