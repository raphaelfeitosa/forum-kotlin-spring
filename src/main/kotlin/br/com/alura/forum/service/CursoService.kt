package br.com.alura.forum.service

import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.model.Curso
import br.com.alura.forum.repository.CursoRepository
import org.springframework.stereotype.Service

@Service
class CursoService(
    private val repository: CursoRepository,
    private val notFoundMessage: String = "Curso nao encontrado!",
) {

    fun buscarPorId(id: Long): Curso {
        val curso = repository.findById(id).orElseThrow() {
            NotFoundException(notFoundMessage)
        }
        return curso
    }
}
