package com.brokyfood.app.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.brokyfood.app.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val adapter = FoodAdapter { food ->
        try {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeToDetail(food)
            )
        } catch (e: Exception) {
            Log.e("HomeFragment", "Error navigating to detail", e)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return try {
            _binding = FragmentHomeBinding.inflate(inflater, container, false)
            binding.root
        } catch (e: Exception) {
            Log.e("HomeFragment", "Error in onCreateView", e)
            View(requireContext())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            setupRecyclerView()
            setupSearch()
            observeUiState()
        } catch (e: Exception) {
            Log.e("HomeFragment", "Error in onViewCreated", e)
        }
    }

    private fun setupRecyclerView() {
        try {
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = this@HomeFragment.adapter
            }
        } catch (e: Exception) {
            Log.e("HomeFragment", "Error setting up RecyclerView", e)
        }
    }

    private fun setupSearch() {
        try {
            binding.searchInput.doOnTextChanged { text, _, _, _ ->
                viewModel.onSearchQueryChanged(text?.toString() ?: "")
            }
        } catch (e: Exception) {
            Log.e("HomeFragment", "Error setting up search", e)
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect { state ->
                        adapter.submitList(state.foods)
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error observing UI state", e)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 