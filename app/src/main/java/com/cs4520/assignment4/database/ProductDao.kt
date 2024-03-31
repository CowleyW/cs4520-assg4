package com.cs4520.assignment4.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cs4520.assignment4.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    suspend fun getAll(): List<Product>

    @Insert
    suspend fun insertAll(vararg products: Product)

    @Query("DELETE FROM product")
    suspend fun deleteAll()
}