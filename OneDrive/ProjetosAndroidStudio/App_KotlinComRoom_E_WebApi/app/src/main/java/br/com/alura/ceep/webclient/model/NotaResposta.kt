package br.com.alura.ceep.webclient

import br.com.alura.ceep.model.Nota
import java.util.*

class NotaResposta(
    val id : String?,
    val titulo :String?,
    val descrição: String?,
    val imagem : String?
) {

    val nota : Nota get() = Nota(
        id = id?: UUID.randomUUID().toString(),
        titulo = titulo ?: "",
        descricao = descrição ?: "",
        imagem = imagem ?: "")

}