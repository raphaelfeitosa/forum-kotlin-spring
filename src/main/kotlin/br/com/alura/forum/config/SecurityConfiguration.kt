package br.com.alura.forum.config

import br.com.alura.forum.security.JWTAuthenticationFilter
import br.com.alura.forum.security.JWTLoginFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val userDetailsService: UserDetailsService,
    private val jwtUtil: JWTUtil

) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.
        csrf()?.disable()?.//Protege contra atack maliciosos
        authorizeRequests()?.
        antMatchers("/h2-console/**")?.permitAll()?.
        antMatchers("/topicos/**")?.hasAuthority("LEITURA_ESCRITA")?.
        antMatchers(HttpMethod.POST, "/login")?.permitAll()?.
        anyRequest()?.authenticated()?.
        and()
        http?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http?.addFilterBefore(
            JWTLoginFilter(authManager = authenticationManager(), jwtUtil = jwtUtil),
            UsernamePasswordAuthenticationFilter().javaClass
        )
        http?.addFilterBefore(
            JWTAuthenticationFilter(jwtUtil = jwtUtil),
            UsernamePasswordAuthenticationFilter().javaClass
        )
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)?.passwordEncoder(bCryptPasswordEncoder())
    }

    override fun configure(web: WebSecurity?) {
       web?.ignoring()?.
        antMatchers("/h2-console/**");
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}