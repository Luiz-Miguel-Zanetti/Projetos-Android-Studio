package com.example.marvelappstarter.data.remote

import retrofit2.http.Query
import com.example.marvelappstarter.data.model.character.CharacterModel
import com.example.marvelappstarter.data.model.character.CharacterModelResponse
import com.example.marvelappstarter.data.model.comic.ComicModelResponse
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {

    @GET("characters")
    suspend fun list(@Query("nameStartsWith") nameStartsWith: String? = null):
            Response<CharacterModelResponse>

    @GET("characters/{characterId}/comics")
    suspend fun getComics(
        @Path(value = "characterId", encoded = true,) characterId: Int ): Response<ComicModelResponse>

}