package me.hawkweisman

import java.math.BigInteger

import org.json.{JSONArray, JSONObject}

/**
  * Created by Eliza on 7/21/16.
  */
package object jsonQuery
extends UnboxedUnion {

  type AnyEnum[E <: Enum[E]] = Enum[E]

  /**
    * Types we can extract from JSON:
    *  + `org.json`'s  [[JSONArray]] or [[JSONObject]]
    *  + numbers: `Double`, `Int`
    *  + `Boolean` values
    *  + `String`s
    *  + [[java.lang.Enum]]s
    *
    */
  // Todo: extract case classes as well?
  type FromJson
    = ∅ ∨ JSONObject ∨ JSONArray ∨ AnyEnum[_] ∨ Boolean ∨ Double ∨ Int ∨
          String ∨ BigDecimal ∨ BigInteger



}
