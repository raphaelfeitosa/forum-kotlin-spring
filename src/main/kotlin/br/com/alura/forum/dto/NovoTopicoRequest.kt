package br.com.alura.forum.dto

import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class NovoTopicoRequest(
    @field:Schema(description = "Titulo para criar novo topico", example = "Programação Orientada a Objetos")
    @field:NotEmpty(message = "Titulo nao pode ser em branco")
    @field:Size(min = 5, max = 100, message = "Titulo deve ter entre 5 e 100 caracteres")
    val titulo: String,
    @field:Schema(
        description = "Mensagem referente ao topico que deseja ser criado",
        example = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"
    )
    @field:NotEmpty(message = "Mensagem nao pode ser em branco")
    val mensagem: String,
    @field:Schema(description = "ID do curso que deseja abrir o topico", example = "2")
    @field:NotNull
    val idCurso: Long,
    @field:Schema(description = "Id do autor que deseja criar o topico", example = "1")
    @field:NotNull
    val idAutor: Long
)
