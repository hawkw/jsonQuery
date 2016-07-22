//package me.hawkweisman
//
//import me.hawkweisman.jsonQuery.UnboxedUnion
//import org.scalatest.{FlatSpec, Matchers}
//
///**
//  * Created by hawk on 7/21/16.
//  */
//class UnboxedUnionLaws
//extends FlatSpec
//  with Matchers
//  with UnboxedUnion {
//
//  "The union of (A ∨ B)" should "implicitly include ¬¬A" in {
//    implicitly[Int : (∅ ∨ Int ∨ String)#Element]
//  }
//  it should "implicitly include ¬¬B" in {
//    implicitly[String : (∅ ∨ Int ∨ String)#Element]
//  }
//  it should "not include ¬¬C" in {
//    "implicitly[Double <:< (∅ ∨ Int ∨ String)#Element]" shouldNot compile
//  }
//
//}
