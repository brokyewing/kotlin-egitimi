package com.brokyfood.app.data.api

import com.brokyfood.app.data.model.CartFood
import com.brokyfood.app.data.model.Food
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodApiService {
    @GET("tumYemekleriGetir.php")
    suspend fun getAllFoods(): FoodResponse

    @POST("sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun getCartFoods(
        @Field("kullanici_adi") username: String
    ): CartResponse

    @POST("sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addToCart(
        @Field("yemek_adi") name: String,
        @Field("yemek_resim_adi") imageUrl: String,
        @Field("yemek_fiyat") price: Int,
        @Field("yemek_siparis_adet") quantity: Int,
        @Field("kullanici_adi") username: String
    ): BaseResponse

    @POST("sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun removeFromCart(
        @Field("sepet_yemek_id") cartItemId: Int,
        @Field("kullanici_adi") username: String
    ): BaseResponse
}

data class FoodResponse(
    val success: Int = 0,
    val yemekler: List<Food>? = null
)

data class CartResponse(
    val success: Int = 0,
    val sepet_yemekler: List<CartFood>? = null
)

data class BaseResponse(
    val success: Int = 0,
    val message: String? = null
) 