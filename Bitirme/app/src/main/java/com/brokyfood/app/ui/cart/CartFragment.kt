package com.brokyfood.app.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.brokyfood.app.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels()
    private val adapter = CartAdapter(
        onRemoveClick = { cartItem ->
            viewModel.removeFromCart(cartItem.cartId)
        },
        onQuantityChanged = { cartItem, newQuantity ->
            viewModel.updateQuantity(cartItem, newQuantity)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupCheckoutButton()
        observeUiState()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CartFragment.adapter
        }
    }

    private fun setupCheckoutButton() {
        binding.checkoutButton.setOnClickListener {
            // Siparişi tamamla işlemi
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.emptyState.visibility = if (state.cartItems.isEmpty()) View.VISIBLE else View.GONE
                    binding.orderSummaryCard.visibility = if (state.cartItems.isNotEmpty()) View.VISIBLE else View.GONE
                    binding.checkoutButton.visibility = if (state.cartItems.isNotEmpty()) View.VISIBLE else View.GONE

                    adapter.submitList(state.cartItems)
                    binding.itemCount.text = getString(com.brokyfood.app.R.string.items_count, state.cartItems.sumOf { it.quantity })
                    binding.totalPrice.text = getString(com.brokyfood.app.R.string.price_format, state.totalPrice)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 