package com.example.novoprojeto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.novoprojeto.database.AppDatabase
import com.example.novoprojeto.databinding.FragmentProductListBinding
import kotlinx.coroutines.launch

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)

        adapter = ProductAdapter()
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewProducts.adapter = adapter

        binding.btnAddProduct.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ProductAddFragment())
                .addToBackStack(null)
                .commit()
        }

        loadProducts()
        return binding.root
    }

    private fun loadProducts() {
        val productDao = AppDatabase.getDatabase(requireContext()).productDao()
        lifecycleScope.launch {
            productDao.getAllProducts().collect { products ->
                adapter.submitList(products)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
