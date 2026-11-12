package com.brokyfood.app.data.repository

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
        val response = foodApiService.getAllFoods()
        if (response.success == 1) {
            emit(response.yemekler ?: emptyList())
        } else {
            emit(emptyList())
        }
    }

    fun getCartFoods(username: String): Flow<List<CartFood>> = flow {
        val response = foodApiService.getCartFoods(username)
        if (response.success == 1) {
            emit(response.sepet_yemekler ?: emptyList())
        } else {
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
            response.success == 1
        } catch (_: Exception) {
            false
        }
    }

    suspend fun updateCartQuantity(cartFood: CartFood, newQuantity: Int, username: String): Boolean {
        return try {
            val response = foodApiService.updateCartQuantity(
                cartItemId = cartFood.cartId,
                username = username,
                quantity = newQuantity
            )
            response.success == 1
        } catch (_: Exception) {
            // Legacy fallback if talking to a server without sepetAdetGuncelle.php
            try {
                val removed = removeFromCart(cartFood.cartId, username)
                if (removed && newQuantity > 0) {
                    addToCart(
                        Food(cartFood.cartId, cartFood.name, cartFood.imageUrl, cartFood.price),
                        newQuantity,
                        username
                    )
                } else {
                    removed
                }
            } catch (_: Exception) {
                false
            }
        }
    }

    suspend fun removeFromCart(cartItemId: Int, username: String): Boolean {
        return try {
            val response = foodApiService.removeFromCart(cartItemId, username)
            response.success == 1
        } catch (_: Exception) {
            false
        }
    }

    suspend fun checkout(username: String): Boolean {
        return try {
            val response = foodApiService.checkout(username)
            response.success == 1
        } catch (_: Exception) {
            false
        }
    }
}
