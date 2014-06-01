package com.bytes32.tevinzi2

import org.scalatest.FreeSpec
import org.scalatest.matchers.ShouldMatchers

class CrudTest extends FreeSpec with ShouldMatchers{


  "CRUD" - {
    val crud = new Crud[(Int, String), Int]{}
    "Create and Read" in {
      crud.save(1, (2, "blah"))
      crud.get(1) should be(Some((2, "blah")))
    }

    "Update" in {
      val crud = new Crud[(Int, String), Int]{}
      crud.save(1, (2, "blah"))
      crud.get(1) should be(Some((2, "blah")))
      crud.save(1, (3, "bam"))
      crud.get(1) should be(Some((3, "bam")))
    }

    "Delete" in {
      val crud = new Crud[(Int, String), Int]{}
      crud.save(2, (2, "POW!"))
      crud.get(2) should be(Some((2, "POW!")))
      crud.remove(2)
      crud.get(2) should be(None)
    }

    "Offset and limits" in {
      val crud = new Crud[(Int, String), Int]{}
      for (count <- 1 to 5) {
        crud.save(count, (count, s"T$count"))
      }
      crud.list(offset = 0, limit = 3) should be (List((1,"T1"),(2,"T2"),(3,"T3")))
      crud.list(offset = 1, limit = 3) should be (List((2,"T2"),(3,"T3"),(4,"T4")))
      crud.remove(2)
      crud.list(offset = 1, limit = 3) should be (List((3,"T3"),(4,"T4"),(5,"T5")))
    }
  }

}
