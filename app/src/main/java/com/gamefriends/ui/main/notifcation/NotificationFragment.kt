package com.gamefriends.ui.main.notifcation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.ui.NotificationAdapter
import com.gamefriends.databinding.FragmentNotificationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private var _binding : FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val viewModel : NotificationViewModel by viewModels()
    private lateinit var adapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        fetchNotifications()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding  = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = NotificationAdapter().apply {
            onItemClick = { data ->
                handleItemClick(data.userRequestId)
            }
        }

        binding.notificationRecylerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@NotificationFragment.adapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
    }

    private fun fetchNotifications() {
        viewModel.fetchListRequestNotification().observe(viewLifecycleOwner) { apiResponse ->
            when (apiResponse) {
                is Resource.Success -> {
                    Log.d("TAG", "API Response: ${apiResponse.data}")
                    adapter.submitList(apiResponse.data?.data)
                    binding.progreesLoading.visibility = View.INVISIBLE
                }
                is Resource.Error -> {
                    Log.d("TAG", "Error fetching notifications: ${apiResponse.message}")
                    binding.progreesLoading.visibility = View.INVISIBLE
                }
                is Resource.Loading -> {
                    Log.d("TAG", "fetchNotifications: Loading")
                    binding.progreesLoading.visibility = View.VISIBLE
                }
            }
        }
    }


    private fun handleItemClick(userRequestId: String?) {
        userRequestId ?: return
        viewModel.acceptFriendRequest(userRequestId).observe(viewLifecycleOwner) { apiResponse ->
            when (apiResponse) {
                is Resource.Error -> Log.d("TAG", "Error accepting friend request: ${apiResponse.message}")
                is Resource.Loading -> Log.d("TAG", "Accepting friend request: Loading")
                is Resource.Success -> {
                    findNavController().navigate(R.id.action_notificationFragment_to_listChatFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }


//    fun getListFetchNotification() {
//        val adapter = NotificationAdapter()
//        val layoutManager = LinearLayoutManager(requireContext())
//        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
//        binding.notificationRecylerView.layoutManager = layoutManager
//        binding.notificationRecylerView.addItemDecoration(itemDecoration)
//
//        viewModel.fetchListRequestNotification().observe(viewLifecycleOwner) {apiResponse ->
//            adapter.submitList(apiResponse.data?.data)
//        }
//
//        binding.notificationRecylerView.adapter = adapter.apply {
//            onItemClick = { data ->
//                viewModel.acceptFriendRequest(data.userRequestId ?: "").observe(viewLifecycleOwner) { apiResponse ->
//                    when(apiResponse) {
//                        is Resource.Error -> Log.d("TAG", "getListFetchNotification: ${apiResponse.message}")
//                        is Resource.Loading -> Log.d("TAG", "getListFetchNotification: Loading")
//                        is Resource.Success -> {
//                            findNavController().navigate(R.id.action_notificationFragment_to_listChatFragment)
//                        }
//                    }
//                }
//            }
//        }
//    }


}