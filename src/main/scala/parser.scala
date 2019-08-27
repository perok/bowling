package fp

import cats.data._
import cats.implicits._

object parser {
  import fastparse._, NoWhitespace._

  def number[_: P]: P[Int] = P(CharIn("0-9").rep(1).!.map(_.toInt))
  def brackets[_: P]: P[Seq[Int]] = P("[" ~/ number.rep(sep = ",") ~ "]")
  def multipleBrackets[_: P]: P[Seq[Seq[Int]]] = P(brackets.rep(sep = ","))

  def toBowlingData(
      in: String
  ): Either[String, NonEmptyList[(Int, Int, Option[Int])]] = {
    parse(
      in,
      parser.multipleBrackets(_)
    ) match {
      case failure: Parsed.Failure =>
        failure.msg.asLeft
      case Parsed.Success(value, _) =>
        value.toList
          .map(_.toList)
          .zipWithIndex
          .traverse {
            case (elem1 :: elem2 :: Nil, _) =>
              (elem1, elem2, None).asRight
            case (elem1 :: elem2 :: elem3 :: Nil, index) =>
              Either.cond(
                index === 9,
                (elem1, elem2, elem3.some),
                s"Turn $index contains an extra try. Only allowed at last turn."
              )
            case (wrongData, index) =>
              s"Turn $index contains too many tries: $wrongData".asLeft
          }
          .flatMap(_.toNel.toRight("No turns inserted"))
          .filterOrElse(_.size === 10, "Icorrect number of turns")
    }
  }
}
