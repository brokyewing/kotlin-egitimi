package com.brokyfood.app.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.brokyfood.app.R
import com.brokyfood.app.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeUiState()
    }

    private fun setupUI() {
        val food = args.food
        binding.apply {
            foodName.text = food.name
            foodPrice.text = getString(R.string.price_format, food.price)
            
            Glide.with(foodImage)
                .load("http://kasimadalan.pe.hu/yemekler/resimler/${food.imageUrl}")
                .into(foodImage)

            quantityPicker.minValue = 1
            quantityPicker.maxValue = 100
            quantityPicker.value = 1

            addToCartButton.setOnClickListener {
                viewModel.addToCart(food, quantityPicker.value)
            }

            // Toolbar'ı ayarla
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.addToCartButton.isEnabled = !state.isLoading
                    binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                    state.messageResId?.let {
                        Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
                        viewModel.messageShown()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 