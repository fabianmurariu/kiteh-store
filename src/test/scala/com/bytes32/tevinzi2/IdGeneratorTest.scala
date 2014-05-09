package com.bytes32.tevinzi2

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import scala.util.Random

class IdGeneratorTest extends FlatSpec with ShouldMatchers {

  val gen = new IdGenerator {}

  "generateId" should "be all letters and numbers with + = and -" in {
    gen.generateId should fullyMatch regex "[0-9a-zA-Z\\+=_]+".r
  }

  behavior of  "toString"

  it should "return 1kprn2df for 123457891011L in base 36" in {
    gen.toString(123457891011L, 36) should be("1kprn2df")
  }

  it should "be exactly as Long.toString(long,radix)" in {
    for (x <- 1 to 100) {
      val base = Random.nextInt(35) + 2
      val long = Random.nextLong()
      gen.toString(long, base).replaceAll("_","-") should be (java.lang.Long.toString(long, base))
    }
  }

  it should "produce 35 as exactly z in base 36" in {
    gen.toString(35, 36) should be("z")
  }

  it should "produce 36, 37 to be exactly A, B in base 38" in {
    gen.toString(36, 38) should be("A")
    gen.toString(37, 38) should be("B")
  }

  it should "produce '=' for 63 in base 64" in {
    gen.toString(63, 64) should be("=")
  }

}
