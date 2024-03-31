package com.cs4520.assignment4

import android.content.Context
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cs4520.assignment4.database.Database
import com.cs4520.assignment4.model.ProductModel

class FreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    private val db by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = Database::class.java,
            name = "products-db"
        ).build()
    }

    val model: ProductModel = ProductModel(db)

    override suspend fun doWork(): Result {
        val data = model.queryApi()

        if (data.isNotEmpty()) {
            model.fillDb(data);
        }

        return Result.success()
    }
}