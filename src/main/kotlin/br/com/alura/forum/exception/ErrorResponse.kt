package br.com.alura.forum.exception

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class ErrorResponse(
        @field:Schema(description = "Data e hora em que o erro ocorreu", example = "2022-02-20T15:45:50.950670312")
        val timestamp: LocalDateTime = LocalDateTime.now(),
        @field:Schema(description = "Status code retornado pela aplicação", example = "404")
        val status: Int,
        @field:Schema(description = "HttpStatus retornado pela aplicaçãoz", example = "NOT_FOUND")
        val error: String,
        @field:Schema(description = "Mensagem de erro retornado pela aplicação", example = "Campo não pode ser nulo")
        val message: String?,
        @field:Schema(description = "path proveniente do erro gerado pela aplicação", example = "/topicos")
        val path: String
)
