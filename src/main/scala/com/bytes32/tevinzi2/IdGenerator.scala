package com.bytes32.tevinzi2

import java.util.UUID

trait IdGenerator {

  val MAX_BASE = 64

  def generateId: String = {
    val uuid = UUID.randomUUID()
    s"${toString(uuid.getLeastSignificantBits, MAX_BASE)}${toString(uuid.getMostSignificantBits, MAX_BASE)}"
  }

  /**
   * Stolen from Long.toString(long, radix)
   * increased to max radix 64
   * @param z
   * @param radix
   * @return
   */
  def toString(z: Long, radix: Int): String = {
    assert(radix >= Character.MIN_RADIX)
    assert(radix <= digits.length)
    var innerI = z
    if (radix == 10)
      return innerI.toString
    val buf: Array[Char] = new Array[Char](65)
    var charPos: Int = 64
    val negative: Boolean = innerI < 0
    if (!negative) {
      innerI = -innerI
    }
    while (innerI <= -radix) {
      charPos -= 1
      buf(charPos + 1) = digits((-(innerI % radix)).asInstanceOf[Int])
      innerI = innerI / radix
    }
    buf(charPos) = digits((-innerI).asInstanceOf[Int])
    if (negative) {
      charPos -= 1
      buf(charPos) = '_'
    }
    new String(buf, charPos, 65 - charPos)
  }

  /**
   * All possible chars for representing a number as a String
   */
  private val digits: Array[Char] = Array(
    '0', '1', '2', '3', '4', '5',
    '6', '7', '8', '9', 'a', 'b',
    'c', 'd', 'e', 'f', 'g', 'h',
    'i', 'j', 'k', 'l', 'm', 'n',
    'o', 'p', 'q', 'r', 's', 't',
    'u', 'v', 'w', 'x', 'y', 'z',
    'A', 'B', 'C', 'D', 'E', 'F',
    'G', 'H', 'I', 'J', 'K', 'L',
    'M', 'N', 'O', 'P', 'Q', 'R',
    'S', 'T', 'U', 'V', 'W', 'X',
    'Y', 'Z', '|', '='
  )
}
