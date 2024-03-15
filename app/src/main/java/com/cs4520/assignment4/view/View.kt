package com.cs4520.assignment4.view

import com.cs4520.assignment4.model.Product

interface View {
    fun showLoadingBar()
    fun displayError()
    fun showProducts(products: List<Product>)
}