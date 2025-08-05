package com.brokyfood.app.data.repository

import android.util.Log
import com.brokyfood.app.data.api.FoodApiService
import com.brokyfood.app.data.model.CartFood
import com.brokyfood.app.data.model.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val foodApiService: FoodApiService
) {
    fun getAllFoods(): Flow<List<Food>> = flow {
        try {
            val response = foodApiService.getAllFoods()
            Log.d("FoodRepository", "API Response: $response")
            if (response.success == 1) {
                emit(response.yemekler ?: emptyList())
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            Log.e("FoodRepository", "Error fetching foods", e)
            emit(emptyList())
        }
    }

    fun getCartFoods(username: String): Flow<List<CartFood>> = flow {
        try {
            val response = foodApiService.getCartFoods(username)
            if (response.success == 1) {
                emit(response.sepet_yemekler ?: emptyList())
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            Log.e("FoodRepository", "Error fetching cart", e)
            emit(emptyList())
        }
    }

    suspend fun addToCart(food: Food, quantity: Int, username: String): Boolean {
        return try {
            val response = foodApiService.addToCart(
                name = food.name,
                imageUrl = food.imageUrl,
                price = food.price,
                quantity = quantity,
                username = username
            )
            Log.d("FoodRepository", "Add to cart response: $response")
            response.success == 1
        } catch (e: Exception) {
            Log.e("FoodRepository", "Error adding to cart", e)
            false
        }
    }

    suspend fun removeFromCart(cartItemId: Int, username: String): Boolean {
        return try {
            val response = foodApiService.removeFromCart(cartItemId, username)
            response.success == 1
        } catch (e: Exception) {
            Log.e("FoodRepository", "Error removing from cart", e)
            false
        }
    }

    suspend fun getCartFoodsOnce(username: String): List<CartFood> {
        return try {
            val response = foodApiService.getCartFoods(username)
            if (response.success == 1) {
                response.sepet_yemekler ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("FoodRepository", "Error fetching cart", e)
            emptyList()
        }
    }
} 