package com.gamefriends.ui.main.home

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.gamefriends.R
import com.gamefriends.core.data.source.Resource
import com.gamefriends.core.service.MyWorker
import com.gamefriends.core.ui.LoadingStateAdapter
import com.gamefriends.core.ui.UserAdapter
import com.gamefriends.core.utils.CreateSnapHelper
import com.gamefriends.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var workManager: WorkManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workManager = WorkManager.getInstance(requireContext())
        startPeriodicTask()

        getListFeedData()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    private fun getListFeedData() {
        val adapter = UserAdapter()

        val layoutManager = LinearLayoutManager(requireContext())
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.listFeedRv.layoutManager = layoutManager
        binding.listFeedRv.addItemDecoration(itemDecoration)

        val snapHelper = CreateSnapHelper()

        snapHelper.attachToRecyclerView(binding.listFeedRv)

        binding.listFeedRv.adapter = adapter.apply {
            withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter {
                    adapter.retry()
                },
                footer = LoadingStateAdapter {
                    adapter.retry()

                }
            )

            onItemCLick = { data ->
                viewModel.addFriendRequest(data.userId).observe(viewLifecycleOwner) { response ->
                    when(response) {
                        is Resource.Error -> Log.d("FRIEND REQUEST", "Add Friend Request: Error")
                        is Resource.Loading -> Log.d("FRIEND REQUEST", "Add Friend Request: Loading")
                        is Resource.Success -> {
                            Log.d("FRIEND REQUEST", "Add Friend Request: Success")
                        }
                    }
                }
            }

            lifecycleScope.launch {
                viewModel.fetchContentFeed().collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }
    }

    private fun startPeriodicTask() {
        val workRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 3, TimeUnit.HOURS).build()

        // Check if the work is already enqueued
        WorkManager.getInstance(requireContext()).getWorkInfosForUniqueWorkLiveData("unique_work_name").observe(viewLifecycleOwner) { workInfos ->
            if (workInfos.isNullOrEmpty()) {
                // Enqueue only if not already enqueued
                WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                    "unique_work_name",
                    ExistingPeriodicWorkPolicy.REPLACE, // Replace if it already exists
                    workRequest
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}