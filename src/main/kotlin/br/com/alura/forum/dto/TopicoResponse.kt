package br.com.alura.forum.dto

import br.com.alura.forum.model.StatusTopico
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalDateTime

data class TopicoResponse(
    @field:Schema(description = "ID do topico retornado pela aplicação", example = "1")
    val id: Long?,
    @field:Schema(description = "Titulo do topico retornado pela aplicação", example = "Test Driver Development")
    val titulo: String,
    @field:Schema(
        description = "Mensagem do topico retornado pela aplicação",
        example = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"
    )
    val mensagem: String,
    @field:Schema(description = "Estado do topico retornado pela aplicação", example = "NAO_RESPONDIDO")
    val status: StatusTopico,
    @field:Schema(
        description = "Data e hora do cadastro do topico na base de dados",
        example = "2022-02-20T15:45:50.950670312"
    )
    val dataCriacao: LocalDateTime,
    val dataAlteracao: LocalDate?
)
