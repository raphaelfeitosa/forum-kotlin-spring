package br.com.alura.forum.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken


data class LoginRequest(
    @field:Schema(description = "Email para realizar autenticação, na aplicação", example = "johndoe@email.com")
    val email: String?,
    @field:Schema(description = "Senha para autenticar na aplicação", example = "senha")
    val senha: String?
) {
    fun converter(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, senha)
    }
}