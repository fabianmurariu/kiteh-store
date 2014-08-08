package com.bytes32.tevinzi2.data

import com.bytes32.tevinzi2.data.Currency.Currency
import scala.util.Try

case class Checkout(_id: Option[String] = None, items: List[Item] = List.empty, savings: List[Saving] = List.empty) {
  /**
   * Total price including savings
   * @return
   */
  def total()(implicit defaultCurrency: Currency = Currency.NONE): Price = {

    if (items.isEmpty)
      return Price(0, defaultCurrency)

    val totalWithPerItemSavings: Price = items.map {
      case Item(_, _, _, itemPrice, Some(Saving(_, _, _, savingPrice)), quantity) =>
        assert(itemPrice.currency == savingPrice.currency)
        (itemPrice + savingPrice) * quantity
      case Item(_, _, _, itemPrice, None, quantity) =>
        itemPrice * quantity
    }.reduce(_ + _)

    val extraSavings: Price = if (savings.nonEmpty) {
      savings.map(saving => {
        assert(saving.price.currency == totalWithPerItemSavings.currency)
        saving.price
      }).reduce(_ + _)
    } else {
      Price(0, defaultCurrency)
    }

    totalWithPerItemSavings + extraSavings
  }
}

case class Item(_id: String, description: String, image: Image, price: Price, promotion: Option[Saving] = None, quantity: Int = 1)

case class Price(value: Long, currency: Currency) {
  def +(p2: Price): Price = {
    assert(currency == p2.currency)
    Price(value + p2.value, currency)
  }

  def *(quantity: Int): Price = Price(this.value * quantity, this.currency)

}

case class Saving(_id: String, description: String, code: Option[String] = None, price: Price)
