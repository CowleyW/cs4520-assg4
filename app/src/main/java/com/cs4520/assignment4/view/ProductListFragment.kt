package com.cs4520.assignment4.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.cs4520.assignment4.model.ProductAdapter
import com.cs4520.assignment4.R
import com.cs4520.assignment4.database.Database
import com.cs4520.assignment4.databinding.ProductListFragmentBinding
import com.cs4520.assignment4.model.Product
import com.cs4520.assignment4.viewmodel.MVVMProductViewModel
import com.cs4520.assignment4.viewmodel.ProductViewModel
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListFragment : com.cs4520.assignment4.view.View, Fragment() {
    private var _binding: ProductListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProductListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Room.databaseBuilder(requireContext(), Database::class.java, "product-db").build()
        val viewModel: ProductViewModel = ViewModelProvider(this)[MVVMProductViewModel::class.java]
        viewModel.usingDb(db, this)

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.onVisit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showLoadingBar() {
        val loadingBar = binding.root.findViewById<LinearProgressIndicator>(R.id.progress_bar)
        loadingBar.visibility = View.VISIBLE
    }

    override fun displayError() {
        val failureText = binding.root.findViewById<TextView>(R.id.failure_text)
        failureText.visibility = View.VISIBLE

        val loadingBar = binding.root.findViewById<LinearProgressIndicator>(R.id.progress_bar)
        loadingBar.visibility = View.GONE
    }

    override fun showProducts(products: List<Product>) {
        val productsView = binding.root.findViewById<RecyclerView>(R.id.recycler_product_list)
        productsView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = ProductAdapter(products)
        productsView.adapter = adapter
    }
}
