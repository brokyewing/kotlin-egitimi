package com.example.bitirmeprojesi

import com.brokyfood.app.data.api.BaseResponse
import com.brokyfood.app.data.api.CartResponse
import com.brokyfood.app.data.api.FoodApiService
import com.brokyfood.app.data.api.FoodResponse
import com.brokyfood.app.data.model.CartFood
import com.brokyfood.app.data.model.Food
import com.brokyfood.app.data.repository.FoodRepository
import com.brokyfood.app.ui.cart.CartViewModel
import com.brokyfood.app.ui.home.HomeViewModel
import com.brokyfood.app.util.UserProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest {
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `homeViewModel loads and filters foods case-insensitively`() = runTest {
        val fakeApi = FakeFoodApiService()
        val repo = FoodRepository(fakeApi)
        val vm = HomeViewModel(repo)

        assertEquals(2, vm.uiState.value.foods.size)
        vm.onSearchQueryChanged("bak")
        assertEquals(1, vm.uiState.value.foods.size)
        assertEquals("Baklava", vm.uiState.value.foods[0].name)
    }

    @Test
    fun `cartViewModel calculates total price and completes checkout`() = runTest {
        val fakeApi = FakeFoodApiService()
        val repo = FoodRepository(fakeApi)
        val userProvider = object : UserProvider {
            override val username: String = "test_user_1"
        }
        assertTrue(repo.addToCart(Food(1, "Baklava", "baklava.png", 250), 2, "test_user_1"))
        val vm = CartViewModel(repo, userProvider)

        assertEquals(1, vm.uiState.value.cartItems.size)
        assertEquals(500, vm.uiState.value.totalPrice)

        vm.checkout()
        assertEquals(0, vm.uiState.value.cartItems.size)
        assertEquals(0, vm.uiState.value.totalPrice)
    }

    private class FakeFoodApiService : FoodApiService {
        private val foods = listOf(
            Food(1, "Ayran", "ayran.png", 30),
            Food(2, "Baklava", "baklava.png", 250)
        )
        private val cart = mutableListOf<CartFood>()
        private var nextId = 1

        override suspend fun getAllFoods(): FoodResponse =
            FoodResponse(success = 1, yemekler = foods)

        override suspend fun getCartFoods(username: String): CartResponse =
            CartResponse(success = 1, sepet_yemekler = cart.filter { it.username == username })

        override suspend fun addToCart(
            name: String,
            imageUrl: String,
            price: Int,
            quantity: Int,
            username: String
        ): BaseResponse {
            cart.add(CartFood(nextId++, name, imageUrl, price, quantity, username))
            return BaseResponse(success = 1, message = "ok")
        }

        override suspend fun updateCartQuantity(
            cartItemId: Int,
            username: String,
            quantity: Int
        ): BaseResponse {
            val idx = cart.indexOfFirst { it.cartId == cartItemId && it.username == username }
            if (idx < 0) return BaseResponse(success = 0)
            if (quantity <= 0) {
                cart.removeAt(idx)
            } else {
                cart[idx] = cart[idx].copy(quantity = quantity)
            }
            return BaseResponse(success = 1)
        }

        override suspend fun removeFromCart(cartItemId: Int, username: String): BaseResponse {
            val removed = cart.removeIf { it.cartId == cartItemId && it.username == username }
            return BaseResponse(success = if (removed) 1 else 0)
        }

        override suspend fun checkout(username: String): BaseResponse {
            cart.removeIf { it.username == username }
            return BaseResponse(success = 1)
        }
    }
}
