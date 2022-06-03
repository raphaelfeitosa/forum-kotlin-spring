package br.com.alura.forum.dto

import io.swagger.v3.oas.annotations.media.Schema

data class TopicoPorCategoriaResponse(
        @field:Schema(description = "Categoria do relatorio retornado pela aplicação", example = "HTML")
        val categoria: String,
        @field:Schema(description = "Quantidade topico criado por categoria", example = "20")
        val quantidade: Long
)
