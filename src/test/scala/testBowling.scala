package fp

import utest._
import cats.implicits._

object testBowling extends TestSuite {
  def checkBowlingScore(
      expectedResult: Int
  )(implicit testPath: utest.framework.TestPath) = {
    println(testPath.value.last)

    val ourScore = parser
      .toBowlingData(testPath.value.last)
      .map(bowling.calculate(_))

    assert(ourScore == expectedResult.asRight)
  }

  val tests = Tests {
    "[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0],[0,0]" -
      checkBowlingScore(0)

    "[3,3],[3,3],[3,3],[3,3],[3,3],[3,3],[3,3],[3,3],[3,3],[3,3]" -
      checkBowlingScore(60)

    "[4,6],[4,6],[4,6],[4,6],[4,6],[4,6],[4,6],[4,6],[4,6],[4,6,4]" -
      checkBowlingScore(140)

    "[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[0,0]" -
      checkBowlingScore(240)

    "[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,10,10]" -
      checkBowlingScore(300)

    "[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0,0]" -
      checkBowlingScore(270)

    "[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,10,0]" -
      checkBowlingScore(290)

    "[2,4],[0,10],[7,0],[10,0],[6,2],[9,1],[3,3],[10,0],[10,0],[0,0,0]" -
      checkBowlingScore(105)

    "[2,4],[0,10],[7,0],[10,0],[6,2],[9,1],[3,3],[10,0],[10,0],[10,7,2]" -
      checkBowlingScore(151)
  }
}
