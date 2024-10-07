package com.gamefriends.ui.main.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.ui.ListChatsAdapter
import com.gamefriends.databinding.FragmentListChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListChatFragment : Fragment() {

    private var _binding: FragmentListChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListChatViewModel by viewModels()
    private lateinit var adapter: ListChatsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchListChatUser()
        setupRecylerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecylerView() {
        adapter = ListChatsAdapter()

        binding.listChatRecylerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ListChatFragment.adapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL) )
        }
    }
    private fun fetchListChatUser() {
        viewModel.fetchListChat().observe(viewLifecycleOwner) { apiResponse ->
            when (apiResponse) {
                is Resource.Error -> binding.progreesLoading.visibility = View.INVISIBLE
                is Resource.Loading -> binding.progreesLoading.visibility = View.VISIBLE
                is Resource.Success -> {
                    adapter.submitList(apiResponse.data?.data?.listUserChat?.getListMessage)
                    binding.progreesLoading.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }


}