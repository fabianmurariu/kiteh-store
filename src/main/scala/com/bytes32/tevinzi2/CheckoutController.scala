package com.bytes32.tevinzi2

import com.twitter.finatra.Controller

class CheckoutController extends Controller{

  post("/checkout/") {
    request =>
      render.status(201).toFuture
  }



}
