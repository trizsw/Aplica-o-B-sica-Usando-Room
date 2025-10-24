package com.example.novoprojeto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.novoprojeto.database.AppDatabase
import com.example.novoprojeto.databinding.FragmentProductAddBinding
import com.example.novoprojeto.model.Product
import kotlinx.coroutines.launch

class ProductAddFragment : Fragment() {

    private var _binding: FragmentProductAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductAddBinding.inflate(inflater, container, false)

        binding.btnSaveProduct.setOnClickListener {
            val name = binding.editProductName.text.toString()
            val priceText = binding.editProductPrice.text.toString()

            if (name.isNotEmpty() && priceText.isNotEmpty()) {
                val price = priceText.toDoubleOrNull() ?: 0.0
                val product = Product(name = name, price = price)
                val dao = AppDatabase.getDatabase(requireContext()).productDao()

                lifecycleScope.launch {
                    dao.insertProduct(product)
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
