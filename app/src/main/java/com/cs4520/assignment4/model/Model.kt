package com.cs4520.assignment4.model

import com.cs4520.assignment4.Api
import com.cs4520.assignment4.ApiService
import com.cs4520.assignment4.database.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException

interface Model {
    suspend fun fetchData(): List<Product>
    suspend fun queryApi(): List<Product>
    suspend fun fillDb(rows: List<Product>)
}

class ProductModel(private val db: Database) : Model {
    override suspend fun fetchData(): List<Product> {
        return try {
            val data = queryApi()

            if (data.isEmpty()) {
                throw RuntimeException("No data")
            } else {
                fillDb(data)
            }

            println("model: " + data.size)
            data
        } catch(_ : Exception) {
            db.productDao().getAll()
        }
    }

    override suspend fun queryApi(): List<Product> {
        val retrofit = Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        return try {
            service.getData()
        } catch (_ : Exception) {
            emptyList()
        }
    }

    override suspend fun fillDb(rows: List<Product>) {
        println("filling db")
        db.productDao().deleteAll()
        db.productDao().insertAll(*rows.toTypedArray())
    }

}