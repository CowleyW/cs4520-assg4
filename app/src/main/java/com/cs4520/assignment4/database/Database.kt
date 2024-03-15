package com.cs4520.assignment4.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cs4520.assignment4.Product

@Database(entities = [Product::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun productDao() : ProductDao
}