package com.example.novoprojeto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.novoprojeto.database.AppDatabase
import com.example.novoprojeto.databinding.FragmentUserAddBinding
import com.example.novoprojeto.model.User
import kotlinx.coroutines.launch

class UserAddFragment : Fragment() {

    private var _binding: FragmentUserAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserAddBinding.inflate(inflater, container, false)

        binding.btnSaveUser.setOnClickListener {
            val name = binding.editUserName.text.toString()
            val email = binding.editUserEmail.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                val user = User(name = name, email = email)
                val dao = AppDatabase.getDatabase(requireContext()).userDao()

                lifecycleScope.launch {
                    dao.insertUser(user)
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
