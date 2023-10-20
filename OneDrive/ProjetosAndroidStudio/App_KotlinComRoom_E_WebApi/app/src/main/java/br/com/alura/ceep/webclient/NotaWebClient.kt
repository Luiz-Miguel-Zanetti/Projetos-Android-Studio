package br.com.alura.ceep.webclient

import android.util.Log
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webclient.model.NotaRequisição


private const val TAG = "NotaWebClient"

class NotaWebClient {

    private val notaService: NotaService = RetrofitInicializador().notaService

    suspend fun buscaTodas(): List<Nota>? {


        return try {
            val notaRespostas = notaService.buscaTodas()

            notaRespostas.map { notaResposta ->
                notaResposta.nota
            }
        } catch (e: Exception) {
            Log.d(TAG, "buscaTodas: ", e)
            null
        }
    }

    suspend fun salva(nota: Nota): Boolean {
        try {
            val resposta = notaService.salva(
                nota.id,
                NotaRequisição(
                    titulo = nota.titulo,
                    descricao = nota.descricao,
                    imagem = nota.imagem
                )
            )
            return resposta.isSuccessful

        } catch (e: Exception) {
            Log.e(TAG, "Falha ao salvar nota")
        }
        return false
    }

    suspend fun remove(id: String): Boolean {
        try {
            notaService.remove(id)
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Falha ao tentar remover nota")
        }
        return false
    }
}
