package br.com.alura.forum.service

import br.com.alura.forum.dto.AtualizacaoTopicoRequest
import br.com.alura.forum.dto.NovoTopicoRequest
import br.com.alura.forum.dto.TopicoPorCategoriaResponse
import br.com.alura.forum.dto.TopicoResponse
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoRequestMapper
import br.com.alura.forum.mapper.TopicoResponseMapper
import br.com.alura.forum.repository.TopicoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.persistence.EntityManager

@Service
class TopicoService(
    private val repository: TopicoRepository,
    private val topicoResponseMapper: TopicoResponseMapper,
    private val topicoRequestMapper: TopicoRequestMapper,
    private val notFoundMessage: String = "Topico nao encontrado!",
    private val em: EntityManager
) {

    fun listar(
        nomeCurso: String?,
        paginacao: Pageable
    ): Page<TopicoResponse> {
        print(em)
        val topicos = if (nomeCurso == null) {
            repository.findAll(paginacao)
        } else {
            repository.findByCursoNome(nomeCurso = nomeCurso, paginacao = paginacao)
        }
        return topicos.map { t ->
            topicoResponseMapper.map(t)
        }
    }

    fun buscarPorId(id: Long): TopicoResponse {
        val topico = repository.findById(id)
            .orElseThrow { NotFoundException(notFoundMessage) }
        return topicoResponseMapper.map(topico)
    }

    fun cadastrar(request: NovoTopicoRequest): TopicoResponse {
        val topico = topicoRequestMapper.map(request)
        repository.save(topico)
        return topicoResponseMapper.map(topico)
    }

    fun atualizar(request: AtualizacaoTopicoRequest): TopicoResponse {
        val topico = repository.findById(request.id)
            .orElseThrow { NotFoundException(notFoundMessage) }
        topico.titulo = request.titulo
        topico.mensagem = request.mensagem
        return topicoResponseMapper.map(topico)
    }

    fun deletar(id: Long) {
        repository.deleteById(id)
    }

    fun relatorio(): List<TopicoPorCategoriaResponse> {
        return repository.relatorio()
    }
}