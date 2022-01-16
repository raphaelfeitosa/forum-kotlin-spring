package br.com.alura.forum.mapper

import br.com.alura.forum.dto.NovoTopicoRequest
import br.com.alura.forum.model.Topico
import br.com.alura.forum.service.CursoService
import br.com.alura.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class TopicoRequestMapper(
        private val cursoService: CursoService,
        private val usuarioService: UsuarioService
): Mapper<NovoTopicoRequest, Topico> {
    override fun map(t: NovoTopicoRequest): Topico {
        return Topico(
                titulo = t.titulo,
                mensagem = t.mensagem,
                curso = cursoService.buscarPorId(t.idCurso),
                autor = usuarioService.buscarPorId(t.idAutor)
        )
    }

}
