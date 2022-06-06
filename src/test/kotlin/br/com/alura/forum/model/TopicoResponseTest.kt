package br.com.alura.forum.model

import br.com.alura.forum.dto.TopicoResponse
import java.time.LocalDate
import java.time.LocalDateTime

object TopicoResponseTest {

    fun build() =
        TopicoResponse(
            id = 1,
            titulo = "Kotlin",
            mensagem = "Mensagem",
            status = StatusTopico.NAO_RESPONDIDO,
            dataCriacao = LocalDateTime.now().minusDays(2),
            dataAlteracao = LocalDate.now().plusDays(5),
        )

}