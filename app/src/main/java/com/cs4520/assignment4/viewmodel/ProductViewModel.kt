package com.cs4520.assignment4.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.cs4520.assignment4.R
import com.cs4520.assignment4.database.Database
import com.cs4520.assignment4.model.Product
import com.cs4520.assignment4.model.ProductModel
import kotlinx.coroutines.flow.Flow

interface ProductViewModel {
    fun withDB(db: Database)
    fun getRowData(type: String): Pair<Color, Int>
    suspend fun getProducts(): List<Product>
}

class MVVMProductViewModel() : ProductViewModel, ViewModel() {
    private lateinit var productModel: ProductModel

    override fun withDB(db: Database) {
        productModel = ProductModel(db)
    }

    override suspend fun getProducts(): List<Product> {
        val data = productModel.fetchData()

        println("viewmodel: " + data.size)
        return data
    }

    override fun getRowData(type : String): Pair<Color, Int> {
        return if (type == "Food") {
            Pair(
                Color(0xFFFFD965),
                R.drawable.food_cs4520
            )
        } else {
            Pair(
                Color(0xFFE06666),
                R.drawable.equipment_cs4520
            )
        }
    }
}