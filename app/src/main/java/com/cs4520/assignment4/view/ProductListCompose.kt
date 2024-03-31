package com.cs4520.assignment4.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cs4520.assignment4.R
import com.cs4520.assignment4.model.Product
import com.cs4520.assignment4.viewmodel.MVVMProductViewModel
import com.cs4520.assignment4.viewmodel.ProductViewModel

@Composable
fun ProductListCompose(viewModel: ProductViewModel) {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    var loading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        loading = true;
        products = viewModel.getProducts()
        loading = false
    }

    if (loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .padding(horizontal = 16.dp),
            )
        }
    } else if (products.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No Products Available", fontSize = 30.sp)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(products) { p ->
                ProductRow(p, viewModel)
            }
        }
    }
}

@Composable
fun ProductRow(p: Product, viewModel: ProductViewModel) {
    val (color, image) = viewModel.getRowData(p.type)

    Row(
        modifier = Modifier
            .background(color)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Food",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .padding(20.dp)
        )

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = p.name, fontSize = 18.sp)

            if (p.expiryDate != null) {
                Text(p.expiryDate)
            }

            Text("$ " + p.price)
        }
    }
}