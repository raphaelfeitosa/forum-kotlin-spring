package br.com.alura.forum.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class AtualizacaoTopicoRequest(
    @field:Schema(description = "ID do topico necessario para buscar o topico na base de dados", example = "10")
    @field:NotNull
    val id: Long,
    @field:Schema(description = "Novo titulo do topico para ser alterado", example = "Estrutuda de Dados")
    @field:NotEmpty
    @field:Size(min = 5, max = 100)
    val titulo: String,
    @field:Schema(description = "Nova mensagem do topico para ser alterado")
    @field:NotEmpty
    val mensagem: String
)
