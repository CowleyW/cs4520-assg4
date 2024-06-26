package com.cs4520.assignment4.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type : String,
    @ColumnInfo(name = "expiryDate") val expiryDate: String?,
    @ColumnInfo(name = "price") val price: Double
)