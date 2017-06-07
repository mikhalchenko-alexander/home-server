package com.anahoret.home_server.config

import org.apache.tika.config.TikaConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MediaConfig {

  @Bean
  fun tikaConfig(): TikaConfig = TikaConfig()

}
