package com.example.samplerequestapi

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{cep}/json/")
    suspend fun getCepDetails(@Path("cep") cep: String): CepResponse
}