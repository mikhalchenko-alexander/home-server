package com.anahoret

import com.anahoret.home_server.HomeApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(HomeApplication::class))
class HomeApplicationTests {

	@Test
	fun contextLoads() {
	}

}
