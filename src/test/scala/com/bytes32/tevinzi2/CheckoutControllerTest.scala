package com.bytes32.tevinzi2

import org.scalatest.FreeSpec
import org.scalatest.matchers.ShouldMatchers
import com.twitter.finatra.test.MockApp

class CheckoutControllerTest extends FreeSpec with ShouldMatchers with HelperFunc {
  val app = MockApp(new CheckoutController())
  "When a new item is added create a checkout object and return it" - {

    "create the cart and return the id based on one item" - {

    }

    "the cart is already there just add the item" in {

    }

    "you always override the entire cart" in {

    }
  }

}
