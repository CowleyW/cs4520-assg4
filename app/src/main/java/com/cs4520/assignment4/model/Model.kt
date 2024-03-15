package com.cs4520.assignment4.model

import com.cs4520.assignment4.Api
import com.cs4520.assignment4.ApiService
import com.cs4520.assignment4.database.Database
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

interface Model {
    suspend fun fetchData(): List<Product>
    suspend fun fillDb(rows: List<Product>)
}

class ProductModel(private val db : Database): Model {
    override suspend fun fetchData(): List<Product> {
        val retrofit = Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        var response: List<Product> = ArrayList()

            try {
                response = service.getData()
                print("Got data" + response.size)
            } catch (_: Exception) {
                print("Failed to get data")
            }

        print("Size: " + response.size)
        return response
    }

    override suspend fun fillDb(rows: List<Product>) {
        db.productDao().deleteAll()
        db.productDao().insertAll(*rows.toTypedArray())
    }

}