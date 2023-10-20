package br.com.alura.ceep.webclient

import androidx.room.Insert
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webclient.model.NotaRequisição
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface NotaService {

    @GET("notas")
    suspend fun buscaTodas() : List<NotaResposta>

    @PUT("notas/{id}")
   suspend fun salva(@Path("id") id : String, @Body nota : NotaRequisição) :
            Response<NotaResposta>

   @DELETE("NOTA/{id}")
   suspend fun remove(@Path("id") id: String) : Response<Void>

}