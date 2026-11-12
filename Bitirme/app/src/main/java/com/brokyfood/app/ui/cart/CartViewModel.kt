package com.brokyfood.app.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokyfood.app.R
import com.brokyfood.app.data.model.CartFood
import com.brokyfood.app.data.repository.FoodRepository
import com.brokyfood.app.util.UserProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: FoodRepository,
    private val userProvider: UserProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        loadCartItems()
    }

    fun loadCartItems() {
        viewModelScope.launch {
            try {
                repository.getCartFoods(userProvider.username).collect { cartItems ->
                    _uiState.update {
                        it.copy(
                            cartItems = cartItems,
                            totalPrice = cartItems.sumOf { item -> item.price * item.quantity }
                        )
                    }
                }
            } catch (_: Exception) {
                _uiState.update { it.copy(messageResId = R.string.error_occurred) }
            }
        }
    }

    fun removeFromCart(cartItemId: Int) {
        viewModelScope.launch {
            val success = repository.removeFromCart(cartItemId, userProvider.username)
            if (success) {
                loadCartItems()
            } else {
                _uiState.update { it.copy(messageResId = R.string.error_occurred) }
            }
        }
    }

    fun updateQuantity(cartFood: CartFood, newQuantity: Int) {
        viewModelScope.launch {
            val success = repository.updateCartQuantity(cartFood, newQuantity, userProvider.username)
            if (success) {
                loadCartItems()
            } else {
                _uiState.update { it.copy(messageResId = R.string.error_occurred) }
            }
        }
    }

    fun checkout() {
        viewModelScope.launch {
            val success = repository.checkout(userProvider.username)
            if (success) {
                _uiState.update {
                    it.copy(
                        cartItems = emptyList(),
                        totalPrice = 0,
                        messageResId = R.string.order_completed
                    )
                }
            } else {
                _uiState.update { it.copy(messageResId = R.string.error_occurred) }
            }
        }
    }

    fun messageShown() {
        _uiState.update { it.copy(messageResId = null) }
    }
}

data class CartUiState(
    val cartItems: List<CartFood> = emptyList(),
    val totalPrice: Int = 0,
    val messageResId: Int? = null
)
