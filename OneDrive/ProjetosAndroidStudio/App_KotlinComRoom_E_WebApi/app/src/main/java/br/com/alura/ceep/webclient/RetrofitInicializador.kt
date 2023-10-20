package br.com.alura.ceep.webclient

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class RetrofitInicializador {

    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.18.224:8080/").addConverterFactory(MoshiConverterFactory.create())
        .build()

    val notaService : NotaService = retrofit.create(NotaService::class.java)
}