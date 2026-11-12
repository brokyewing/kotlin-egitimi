package com.brokyfood.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brokyfood.app.R
import com.brokyfood.app.data.model.Food
import com.brokyfood.app.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private var allFoods: List<Food> = emptyList()

    init {
        loadFoods()
    }

    fun loadFoods() {
        viewModelScope.launch {
            try {
                repository.getAllFoods().collect { foods ->
                    allFoods = foods
                    _uiState.update { currentState ->
                        currentState.copy(foods = foods, errorResId = null)
                    }
                }
            } catch (_: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(foods = emptyList(), errorResId = R.string.error_occurred)
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        val filteredFoods = if (query.isBlank()) {
            allFoods
        } else {
            allFoods.filter { food ->
                food.name.contains(query, ignoreCase = true)
            }
        }
        _uiState.update { currentState ->
            currentState.copy(foods = filteredFoods)
        }
    }

    fun errorShown() {
        _uiState.update { it.copy(errorResId = null) }
    }
}

data class HomeUiState(
    val foods: List<Food> = emptyList(),
    val errorResId: Int? = null
)
