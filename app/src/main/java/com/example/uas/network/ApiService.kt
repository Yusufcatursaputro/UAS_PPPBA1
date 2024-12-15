package com.example.uas.network

import com.example.uas.model.Data
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("FRviA/anime/")
    fun getAllUsers(): Call<List<Data>>
    @POST("FRviA/anime")
    fun addAnime(@Body anime: Data): Call<Data>
    @DELETE("FRviA/anime/{id}")
    fun deleteAnime(@Path("id") id: String): Call<Void>
}
