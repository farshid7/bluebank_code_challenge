package com.example.bluebanktest.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bluebanktest.R
import com.example.bluebanktest.databinding.FragmentMainBinding
import com.example.bluebanktest.ui.main.adapter.TransactionAdapter
import com.example.bluebanktest.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by viewModels<MainViewModel>()
    private val binding by viewBinding<FragmentMainBinding>()

    private val adapter by lazy {
        TransactionAdapter.build(
            recyclerView = binding.content.recyclerView,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.transaction.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }


    companion object {
        fun newInstance() = MainFragment()
    }
}