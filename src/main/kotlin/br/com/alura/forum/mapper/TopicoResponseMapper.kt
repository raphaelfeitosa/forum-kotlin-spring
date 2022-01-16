package br.com.alura.forum.mapper

import br.com.alura.forum.dto.TopicoResponse
import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Component

@Component
class TopicoResponseMapper: Mapper<Topico, TopicoResponse> {

    override fun map(t: Topico): TopicoResponse {
        return TopicoResponse(
                id = t.id,
                titulo = t.titulo,
                mensagem = t.mensagem,
                dataCriacao = t.dataCriacao,
                status = t.status
        )
    }
}