package br.com.alura.forum.model

object UsuarioTest {

    fun build() = Usuario(
        id = 1,
        nome = "John Doe",
        email = "johndoe@email.com",
        password = "senha"
    )
}