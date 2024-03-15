package com.cs4520.assignment4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.cs4520.assignment4.database.Database
import com.cs4520.assignment4.databinding.ProductListFragmentBinding
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.Exception

class ProductListFragment : Fragment() {
    private var _binding: ProductListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: Database

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = Room.databaseBuilder(requireContext(), Database::class.java, "product-db").build()
        _binding = ProductListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productsView = binding.root.findViewById<RecyclerView>(R.id.recycler_product_list)
        productsView.layoutManager = LinearLayoutManager(requireContext())

        val retrofit = Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.getData()

                if (response.isNotEmpty()) {
                    db.productDao().deleteAll()
                    db.productDao().insertAll(*response.toTypedArray())

                    withContext(Dispatchers.Main) {
                        val adapter = ProductAdapter(response)
                        productsView.adapter = adapter
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        showFailureMessage()
                    }
                }
            } catch (e: Exception) {
                val fromDb = db.productDao().getAll()
                if (fromDb.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        val adapter = ProductAdapter(fromDb)
                        productsView.adapter = adapter
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        showFailureMessage()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showFailureMessage() {
        val failureText = binding.root.findViewById<TextView>(R.id.failure_text)
        failureText.visibility = View.VISIBLE

        val loadingBar = binding.root.findViewById<LinearProgressIndicator>(R.id.progress_bar)
        loadingBar.visibility = View.GONE
    }
}