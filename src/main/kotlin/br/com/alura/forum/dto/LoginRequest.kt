package br.com.alura.forum.dto

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken


data class LoginRequest(
    private var email: String?,
    private var senha: String?
) {
    fun converter(): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(email, senha)
    }
}