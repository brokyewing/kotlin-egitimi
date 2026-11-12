package com.brokyfood.app.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokyfood.app.R
import com.brokyfood.app.data.model.Food
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
class DetailViewModel @Inject constructor(
    private val repository: FoodRepository,
    private val userProvider: UserProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun addToCart(food: Food, quantity: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val success = repository.addToCart(food, quantity, userProvider.username)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        messageResId = if (success) R.string.added_to_cart else R.string.failed_to_add
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        messageResId = R.string.error_occurred
                    )
                }
            }
        }
    }

    fun messageShown() {
        _uiState.update { it.copy(messageResId = null) }
    }
}

data class DetailUiState(
    val isLoading: Boolean = false,
    val messageResId: Int? = null
)
