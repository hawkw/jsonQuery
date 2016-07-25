package me.hawkweisman

import jsonQuery.extractors._, me.hawkweisman.jsonQuery.queries._

import org.json.JSONObject
import org.scalatest.{Matchers, OptionValues, TryValues, WordSpec}

/**
  * Created by Eliza on 7/23/16.
  */
class ExtractorSpec
  extends WordSpec
    with Matchers
    with TryValues
    with OptionValues {

  val simpleJson = new JSONObject(
    """{ "int": 1,
      |  "string": "a string",
      |  "bool": true,
      |  "double": 0.5
           }""".stripMargin)
  
  "The JsonInt extractor" when {
    "used with a query for an int" should {
      "match the int in a pattern match" in {
        simpleJson \ "int" should matchPattern { case JsonInt(_) => }
      }
      "extract the int to the correct value" in {
        val JsonInt(it) = simpleJson \ "int"
        it shouldEqual 1
      }
    }
    "used with queries for other types" should {
      "not match a bool" in {
        simpleJson \ "bool" should not matchPattern { case JsonInt(_) => }
      }
      "not match a double" in {
        simpleJson \ "double" should not matchPattern { case JsonInt(_) => }
      }
      "not match a string" in {
        simpleJson \ "string" should not matchPattern { case JsonInt(_) => }
      }
    }
  }

  "The JsonDouble extractor" when {
    "used with a query for a double" should {
      "match the double  in a pattern match" in {
        simpleJson \ "double" should matchPattern { case JsonDouble(_) => }
      }
      "extract the int to the correct value" in {
        val JsonDouble(it) = simpleJson \ "double"
        it shouldEqual 0.5
      }
    }
    "used with queries for other types" should {
      "not match a bool" in {
        simpleJson \ "bool" should not matchPattern { case JsonDouble(_) => }
      }
      "not match an int" in {
        simpleJson \ "int" should not matchPattern { case JsonDouble(_) => }
      }
      "not match a string" in {
        simpleJson \ "string" should not matchPattern { case JsonDouble(_) => }
      }
    }
  }

  "The JsonBool extractor" when {
    "used with a query for a bool" should {
      "match the bool in a pattern match" in {
        simpleJson \ "bool" should matchPattern { case JsonBool(_) => }
      }
      "extract the int to the correct value" in {
        val JsonBool(it) = simpleJson \ "bool"
        it shouldEqual true
      }
    }
    "used with queries for other types" should {
      "not match a double" in {
        simpleJson \ "double" should not matchPattern { case JsonBool(_) => }
      }
      "not match an int" in {
        simpleJson \ "int" should not matchPattern { case JsonBool(_) => }
      }
      "not match a string" in {
        simpleJson \ "string" should not matchPattern { case JsonBool(_) => }
      }
    }
  }


  "The JsonString extractor" when {
    "used with a query for a bool" should {
      "match the bool in a pattern match" in {
        simpleJson \ "string" should matchPattern { case JsonString(_) => }
      }
      "extract the int to the correct value" in {
        val JsonString(it) = simpleJson \ "string"
        it shouldEqual "a string"
      }
    }
    "used with queries for other types" should {
      "not match a double" in {
        simpleJson \ "double" should not matchPattern { case JsonString(_) => }
      }
      "not match an int" in {
        simpleJson \ "int" should not matchPattern { case JsonString(_) => }
      }
      "not match a bool" in {
        simpleJson \ "bool" should not matchPattern { case JsonString(_) => }
      }
    }
  }

}
