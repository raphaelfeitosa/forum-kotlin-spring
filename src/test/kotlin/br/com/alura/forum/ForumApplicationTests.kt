package br.com.alura.forum

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootTest
class ForumApplicationTests() {

	@Test
	fun contextLoads() {

		val senha = bCryptPasswordEncoder("senha")
		println(senha)
	}

	fun bCryptPasswordEncoder(senha: String): String? {
		return BCryptPasswordEncoder().encode(senha)
	}

}
