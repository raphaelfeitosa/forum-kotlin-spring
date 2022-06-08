package br.com.alura.forum.controller

import br.com.alura.forum.config.TokenService
import br.com.alura.forum.model.UsuarioTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TopicoControllerTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    private lateinit var jwtUtil: TokenService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    private lateinit var mockMvc: MockMvc

    private var token: String? = null

    companion object {
        private const val RECURSO = "/topicos/"
        private const val RECURSO_ID = RECURSO.plus("%s")
    }

    @BeforeEach
    fun setup() {
        token = gerarToken()

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply<DefaultMockMvcBuilder?>(
            SecurityMockMvcConfigurers.springSecurity()
        ).build()
    }

    @Test
    fun `deve retornar codigo 400 quando chamar topicos sem token`() {
        mockMvc.get(RECURSO)
            .andExpect {
                status { isForbidden() }
            }
            .andDo { print() }
    }

    @Test
    fun `deve retornar codigo 200 quando chamar topicos com token valido`() {
        mockMvc.get(RECURSO) {
            headers { token?.let { this.setBearerAuth(it) } }
        }.andExpect { status { isOk() } }
            .andDo { print() }
    }

    @Test
    fun `deve retornar codigo 200 quando chamar topicos com id 1`() {
        mockMvc.get(RECURSO_ID.format(1)) {
            headers { token?.let { this.setBearerAuth(it) } }
        }.andExpect { status { isOk() } }
            .andDo { print() }
    }

    @Test
    fun `deve retornar codigo 404 quando chamar topicos com id invalido`() {
        mockMvc.get(RECURSO_ID.format(3)) {
            headers { token?.let { this.setBearerAuth(it) } }
        }.andExpect { status { isNotFound()} }
            .andDo { print() }
    }

private fun gerarToken(): String? {
    val usuario = UsuarioTest.converter()
    val authentication = authenticationManager.authenticate(usuario)
    return jwtUtil.generateToken(authentication)
}

private fun UsuarioTest.converter() = UsernamePasswordAuthenticationToken(this.build().email, this.build().password)
}
