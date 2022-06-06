package br.com.alura.forum.service

import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoRequestMapper
import br.com.alura.forum.mapper.TopicoResponseMapper
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.TopicoResponseTest
import br.com.alura.forum.model.TopicoTest
import br.com.alura.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*
import kotlin.random.Random

internal class TopicoServiceTest {

    val topicoRepository: TopicoRepository = mockk()
    val topicoRequestMapper: TopicoRequestMapper = mockk()
    val topicoResponseMapper: TopicoResponseMapper = mockk()

    val topico = PageImpl(listOf(TopicoTest.build()))
    val paginacao: Pageable = mockk()

    val topicoService = TopicoService(topicoRepository, topicoResponseMapper, topicoRequestMapper)


    //Aqui estamos usando slot para capturar o objeto topico na hora do map
    //Estamos usando assertThat da lib assertJ para verificar se o objeto topico foi capturado
    @Test
    fun `deve listar topicos a partir do nome do curso`() {
        val expected = slot<Topico>()

        every { topicoRepository.findByCursoNome(any(), any()) } returns topico
        every { topicoResponseMapper.map(capture(expected)) } returns TopicoResponseTest.build()

        val actual = topicoService.listar("kotlin", paginacao)

        verify(exactly = 1) { topicoRepository.findByCursoNome(any(), any()) }
        verify(exactly = 1) { topicoResponseMapper.map(any()) }
        verify(exactly = 0) { topicoRepository.findAll(paginacao) }

        assertThat(expected.captured.titulo).isEqualTo(actual.content[0].titulo)
    }

    @Test
    fun `deve listar todos os topicos quando quando o nome do curso for nulo`() {
        val expected = TopicoTest.build()

        every { topicoRepository.findAll(paginacao) } returns topico
        every { topicoResponseMapper.map(any()) } returns TopicoResponseTest.build()

       val actual =  topicoService.listar(null, paginacao)

        verify(exactly = 1) { topicoRepository.findAll(paginacao) }
        verify(exactly = 0) { topicoRepository.findByCursoNome(any(), any()) }
        verify(exactly = 1) { topicoResponseMapper.map(any()) }

        assertEquals(expected.titulo, actual.content[0].titulo)
    }

    @Test
    fun `deve listar not found exception quando topico nao for achado`() {
        every { topicoRepository.findById(any()) } returns Optional.empty()

        val actual = assertThrows<NotFoundException> {
            topicoService.buscarPorId(Random.nextLong())
        }

        val expected = "Topico nao encontrado!"

        assertEquals(expected, actual.message)
    }

    @Test
    fun `deve listar um topico quando for encontrado pelo id`() {
        val expected = slot<Topico>()

        every { topicoRepository.findById(any()) } returns Optional.of(TopicoTest.build())
        every { topicoResponseMapper.map(capture(expected)) } returns TopicoResponseTest.build()

        val actual = topicoService.buscarPorId(1)

        verify(exactly = 1) { topicoRepository.findById(any()) }
        verify(exactly = 1) { topicoResponseMapper.map(any()) }

        assertThat(expected.captured.titulo).isEqualTo(actual.titulo)
    }
}
