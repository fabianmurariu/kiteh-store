package com.bytes32.tevinzi2

import scala.collection.convert.{DecorateAsScala, DecorateAsJava}
import com.typesafe.config.{ConfigValue, Config}

object JavaConverters extends DecorateAsJava with DecorateAsScala {

  implicit class ConfigToMap(val config: Config) {
    def toMap: Map[String, ConfigValue] = {
      var result = Map[String, ConfigValue]()
      for (cfg <- config.entrySet().asScala) {
        result = result + ((cfg.getKey, cfg.getValue))
      }
      result
    }
  }

}
