package com.cs4520.assignment4

import retrofit2.http.GET

object Api {
    const val BASE_URL: String = "https://kgtttq6tg9.execute-api.us-east-2.amazonaws.com/"
    const val ENDPOINT: String = "prod/"
}

interface ApiService {
    @GET(Api.ENDPOINT)
    suspend fun getData(): List<Product>
}