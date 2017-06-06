package com.anahoret.home_server.controller

import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
@Secured("USER")
class IndexController {

  @GetMapping
  fun index(): String = "index"

}
