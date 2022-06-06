package br.com.alura.forum.model

object TopicoTest {

    fun build() =
        Topico(
            id = 1,
            titulo = "Kotlin",
            mensagem = "Mensagem",
            curso = CursoTest.build(),
            autor = UsuarioTest.build()
        )
}