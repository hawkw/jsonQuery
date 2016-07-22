package me.hawkweisman

import org.scalatest.{FlatSpec, Matchers, OptionValues}

/**
  * Created by Eliza on 7/22/16.
  */
class JsonSpec
extends FlatSpec
  with Matchers
  with OptionValues {

  import jsonQuery._
  import org.json._

  "Querying a simple JSON object" should "extract an integer as an int" in {
    val json = new JSONObject("{ \"element\":1 }")
    (json \ "element").as[Int].value shouldEqual 1
   }
  it should "not extract an integer as a double" in {
    val json = new JSONObject("{ \"element\":1 }")
    (json \ "element").as[Double] should not be 'defined
  }
  it should "not extract an integer as an array" in {
    val json = new JSONObject("{ \"element\":1 }")
    (json \ "element").as[JSONArray] should not be 'defined
  }
  it should "extract a double as a double" in {
    val json = new JSONObject("{ \"element\":1.0 }")
    (json \ "element").as[Double].value shouldEqual 1.0
  }
  it should "not extract an element that does not exist" in {
    val json = new JSONObject("{ \"element\":1.0 }")
    (json \ "elemnett").as[Double] should not be 'defined
  }

}
