package com.cs4520.assignment4.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cs4520.assignment4.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Insert
    fun insertAll(vararg products: Product)

    @Query("DELETE FROM product")
    fun deleteAll()
}