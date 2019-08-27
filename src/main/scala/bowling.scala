package fp

import cats.data._
import cats.implicits._

object bowling {
  type Turns = NonEmptyList[(Int, Int, Option[Int])]

  def scoreSpare(turns: Turns) =
    turns match {
      case (first, _, _) :== _ =>
        first
    }

  def scoreStrike(turns: Turns) =
    turns match {
      case (first, second, _) :== tail =>
        first + second + (if (first === 10) tail.map(scoreSpare).getOrElse(0)
                          else 0)
    }

  def calculate(turns: Turns): Int =
    turns.coflatMap {
      case (first, second, extra) :== maybeTail =>
        first + second + (maybeTail match {
          case Some(tail) =>
            if (first === 10) scoreStrike(tail)
            else if (first + second === 10) scoreSpare(tail)
            else 0
          case None =>
            extra.getOrElse(0)
        })
    }.combineAll
}

object :== {
  def unapply[A](in: NonEmptyList[A]): Option[(A, Option[NonEmptyList[A]])] =
    Some((in.head, in.tail.toNel))
}
