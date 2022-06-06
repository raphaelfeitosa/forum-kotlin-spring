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
import java.time.LocalDate

@Service
class TopicoService(
    private val repository: TopicoRepository,
    private val topicoResponseMapper: TopicoResponseMapper,
    private val topicoRequestMapper: TopicoRequestMapper,
    private val notFoundMessage: String = "Topico nao encontrado!"
) {

    fun listar(
        nomeCurso: String?,
        paginacao: Pageable
    ): Page<TopicoResponse> {
        val topicos = nomeCurso?.let {
            repository.findByCursoNome(nomeCurso = nomeCurso, paginacao = paginacao)
        } ?: repository.findAll(paginacao)

        return topicos.map { topico ->
            topicoResponseMapper.map(topico)
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
        topico.dataAlteracao = LocalDate.now()
        return topicoResponseMapper.map(topico)
    }

    fun deletar(id: Long) {
        repository.deleteById(id)
    }

    fun relatorio(): List<TopicoPorCategoriaResponse> {
        return repository.relatorio()
    }
}