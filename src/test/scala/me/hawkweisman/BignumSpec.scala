package me.hawkweisman

import java.math.BigInteger

import BigInt.javaBigInteger2bigInt

import org.json.JSONObject
import org.scalatest.{Matchers, OptionValues, TryValues, WordSpec}
import jsonQuery.queries._

import scala.util.Success

/**
  * Created by Eliza on 7/23/16.
  */
class BignumSpec
extends WordSpec
  with Matchers
  with TryValues
  with OptionValues {

  val aBigInt = BigInt(Int.MaxValue) + 1
  val aBigDecimal = BigDecimal(Double.MaxValue) + 1

  // NOTE: that these are all ignored becasue org.json's handling of bignums is
  //       Highly Nonsensical, and I am not currently sure how to handle it.
  //          - eliza, 7/23/16
  "A simple JSON object" when {
    "querying for a key that exists" should {
      "extract a big integer as a BigInt" ignore {
        val json = new JSONObject(s"""{"element":$aBigInt}""")
        (json \ "element").as[BigInteger] shouldEqual Success(aBigInt)
      }
      "not extract a big integer as a BigDecimal" ignore {
        val json = new JSONObject(s"""{"element":$aBigInt}""")
        (json \ "element").as[BigDecimal] should be a 'failure
      }
      "extract a big decimalt number as a BigDecimal" ignore {
        val json = new JSONObject(s"""{"element":$aBigDecimal}""")
        (json \ "element").as[BigDecimal] shouldEqual Success(aBigDecimal)
      }
      "not extract a big decimal number as a BigInt" ignore {
        val json = new JSONObject(s"""{"element":$aBigDecimal}""")
        (json \ "element").as[BigInteger] should be a 'failure
      }
    }
  }

}
