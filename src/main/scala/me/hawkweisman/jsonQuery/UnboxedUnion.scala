package me.hawkweisman.jsonQuery

/**
  * An unboxed union type, expressed using the Curry-Howard isomorphism.
  *
  * Created by Eliza on 7/21/16.
  */
trait UnboxedUnion {

  /**
    * The negation of a type `A`
    * @tparam A the type to negate
    */
  type ¬[A] = A => Nothing

  /**
    * The double negation of a type `A`
    * @tparam A the type to negate
    */
  type ¬¬[A] = ¬[¬[A]]

  /**
    * The union of the types `A` and `B`
    *
    * This is expressed using the DeMorgan equivalence:
    * `(A ∨ B) ⇔ ¬(¬A ∧ ¬B)`
    *
    * @tparam A the first type in the union
    * @tparam B the second type in the union
    */
  type ∨[A, B] = ¬[¬[A] with ¬[B]]

  /**
    * A friendlier version of `A ∨ B`, expressed with existential types.
    *
    * This type operator doesn't need an implicit evidence parameter, making it
    * much prettier to use.
    *
    * It also uses a character that's actually on most people's keyboards.
    *
    * @tparam A the first type in the union
    * @tparam B the second type in the union
    */
  type \/[A, B] = { type λ[X] = ¬¬[X] <:< (A ∨ B) }

  type | [A, B] = (A \/ B)

}
