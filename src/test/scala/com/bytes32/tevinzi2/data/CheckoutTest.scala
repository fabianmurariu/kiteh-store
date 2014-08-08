package com.bytes32.tevinzi2.data

import org.scalatest.FreeSpec
import org.scalatest.matchers.ShouldMatchers
import com.bytes32.tevinzi2.data.Currency.{USD, GBP, NONE, EUR}

class CheckoutTest extends FreeSpec with ShouldMatchers {

  "Checkout" - {
    implicit val defaultCurrency = Currency.EUR

    "a an empty checkout has zero price" in {
      Checkout().total() should be(Price(0L, EUR))
    }

    "one item no sale should be the item price" in {
      val item = Item("1", "random item", null, Price(12, EUR))
      Checkout(items = List(item)).total() should be(Price(12, EUR))
    }

    "one item with its own sale should have it added to the final price" in {
      val item = Item("1", "random item", null, Price(12, EUR), promotion = Some(Saving("1", "random", None, Price(-1, EUR))))
      Checkout(items = List(item)).total() should be(Price(11, EUR))
    }

    "a checkout with a saving will add it to the final total" in {
      val item1 = Item("1", "random item 1", null, Price(12, EUR))
      val item2 = Item("2", "random item 2", null, Price(13, EUR))
      val checkoutSaving1 = Saving("3", "some saving 3", Some("1234"), Price(-2, EUR))
      val checkoutSaving2 = Saving("4", "some saving 4", Some("4321"), Price(-2, EUR))
      Checkout(items = List(item1, item2), savings = List(checkoutSaving1, checkoutSaving2)).total() should be(Price(21, EUR))
    }

    "per item saving should be in the same currency or is failed" in {
      val item = Item("1", "random item", null, Price(12, EUR), promotion = Some(Saving("1", "random", None, Price(-1, GBP))))
      intercept[AssertionError] {
        Checkout(items = List(item)).total() should be(Price(12, EUR))
      }
    }

    "One item can have multiple quantities" in {
      val item = Item("1", "random item", null, Price(12, EUR), promotion = Some(Saving("1", "random", None, Price(-1, EUR))), quantity = 2)
      Checkout(items = List(item)).total() should be(Price(22, EUR))
    }

    "checkout saving should be in the same currency or failed" in {
      val item1 = Item("1", "random item 1", null, Price(12, EUR))
      val item2 = Item("2", "random item 2", null, Price(13, EUR))
      val checkoutSaving1 = Saving("3", "some saving 3", Some("1234"), Price(-2, GBP))
      val checkoutSaving2 = Saving("4", "some saving 4", Some("4321"), Price(-2, USD))
      intercept[AssertionError] {
        Checkout(items = List(item1, item2), savings = List(checkoutSaving1, checkoutSaving2)).total() should be(Price(25, EUR))
      }
    }

  }

  "Price" - {
    "adds to another Price" in {
      Price(23, EUR) + Price(12, EUR) should be (Price(35, EUR))
    }
    "adds to another Price even if negative" in {
      Price(23, EUR) + Price(-12, EUR) should be (Price(11, EUR))
    }
  }

}
