package br.com.alura.forum.config

import br.com.alura.forum.service.UsuarioService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenService(
    private val usuarioService: UsuarioService
) {
    @Value("\${jwt.expiration}")
    private lateinit var expiration: String

    @Value("\${jwt.secret}")
    private lateinit var secret: String

//    fun generateToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String? {
    fun generateToken(authentication: Authentication): String? {
    val usuario = authentication.principal as UserDetails
        return Jwts.builder()
            .setSubject(usuario.username)
            .claim("role", usuario.authorities)
            .setExpiration(Date(System.currentTimeMillis() + expiration.toInt()))
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
            .compact()
    }

    //recebe o token formatado e faz a validação
    fun isValid(jwt: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt)
            true
        } catch (exception: IllegalArgumentException) {
            false
        }
    }

    fun getAuthentication(jwt: String?): Authentication {
        val username = Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt).body.subject
        val user = usuarioService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }
}