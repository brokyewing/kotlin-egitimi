package com.brokyfood.app.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokyfood.app.data.model.CartFood
import com.brokyfood.app.data.repository.FoodRepository
import com.brokyfood.app.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            repository.getCartFoods(Constants.USER_NAME).collect { cartItems ->
                _uiState.update {
                    it.copy(
                        cartItems = cartItems,
                        totalPrice = cartItems.sumOf { item -> item.price * item.quantity }
                    )
                }
            }
        }
    }

    fun removeFromCart(cartItemId: Int) {
        viewModelScope.launch {
            try {
                val success = repository.removeFromCart(cartItemId, Constants.USER_NAME)
                if (success) {
                    loadCartItems()
                }
            } catch (e: Exception) {
                // Hata durumunda sessizce devam et
            }
        }
    }

    fun updateQuantity(cartFood: CartFood, newQuantity: Int) {
        viewModelScope.launch {
            try {
                // Önce eski ürünü sil
                val removeSuccess = repository.removeFromCart(cartFood.cartId, Constants.USER_NAME)
                if (removeSuccess) {
                    // Sonra yeni miktarla ekle
                    val addSuccess = repository.addToCart(
                        food = cartFood.toFood(),
                        quantity = newQuantity,
                        username = Constants.USER_NAME
                    )
                    if (addSuccess) {
                        loadCartItems()
                    }
                }
            } catch (e: Exception) {
                // Hata durumunda sessizce devam et
            }
        }
    }
}

data class CartUiState(
    val cartItems: List<CartFood> = emptyList(),
    val totalPrice: Int = 0
)

// CartFood'u Food'a çeviren extension fonksiyon
private fun CartFood.toFood() = com.brokyfood.app.data.model.Food(
    id = cartId,
    name = name,
    imageUrl = imageUrl,
    price = price
) 