package com.anahoret.home_server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class HomeApplication {

  companion object {
    @JvmStatic fun main(args: Array<String>) {
      SpringApplication.run(HomeApplication::class.java, *args)
    }
  }

}
