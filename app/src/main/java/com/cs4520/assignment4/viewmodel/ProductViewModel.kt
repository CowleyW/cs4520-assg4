package com.cs4520.assignment4.viewmodel

import androidx.lifecycle.ViewModel
import com.cs4520.assignment4.database.Database
import com.cs4520.assignment4.model.Model
import com.cs4520.assignment4.model.ProductModel
import com.cs4520.assignment4.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface ProductViewModel {
    fun usingDb(db: Database, frag: View)
    suspend fun onVisit()
}

class MVVMProductViewModel : ProductViewModel, ViewModel() {
    private lateinit var model: Model
    private lateinit var view: View

    override fun usingDb(db: Database, frag: View) {
        model = ProductModel(db)
        view = frag
    }

    override suspend fun onVisit() {
        view.showLoadingBar()
        val products = model.fetchData()
        if (products.isNotEmpty()) {
            model.fillDb(products)

            CoroutineScope(Dispatchers.Main).launch {
                view.showProducts(products)
            }
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                view.displayError()
            }
        }
    }
}