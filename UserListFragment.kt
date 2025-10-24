package com.example.novoprojeto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.novoprojeto.database.AppDatabase
import com.example.novoprojeto.databinding.FragmentUserListBinding
import kotlinx.coroutines.launch

class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)

        adapter = UserAdapter()
        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewUsers.adapter = adapter

        binding.btnAddUser.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, UserAddFragment())
                .addToBackStack(null)
                .commit()
        }

        loadUsers()
        return binding.root
    }

    private fun loadUsers() {
        val userDao = AppDatabase.getDatabase(requireContext()).userDao()
        lifecycleScope.launch {
            userDao.getAllUsers().collect { users ->
                adapter.submitList(users)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
