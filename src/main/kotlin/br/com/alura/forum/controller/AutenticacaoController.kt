package br.com.alura.forum.controller

import br.com.alura.forum.config.TokenService
import br.com.alura.forum.dto.LoginRequest
import br.com.alura.forum.dto.TokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/login")
class AutenticacaoController(
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService
) {

    @PostMapping
    fun autenticar(@RequestBody @Valid loginRequest: LoginRequest): ResponseEntity<Any> {
        val dadosLogin = loginRequest.converter()

        return try {
            val authentication = authenticationManager.authenticate(dadosLogin)
            val token = tokenService.generateToken(authentication)
            val tokenResponse = TokenResponse(token!!, "Bearer")

            ResponseEntity.ok().body(tokenResponse)

        } catch (exception: AuthenticationException) {
            ResponseEntity.badRequest().build()
        }
    }
}