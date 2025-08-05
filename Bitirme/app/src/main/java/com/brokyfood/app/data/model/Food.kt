package com.brokyfood.app.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    @SerializedName("yemek_id")
    val id: Int,
    @SerializedName("yemek_adi")
    val name: String,
    @SerializedName("yemek_resim_adi")
    val imageUrl: String,
    @SerializedName("yemek_fiyat")
    val price: Int
) : Parcelable

@Parcelize
data class CartFood(
    @SerializedName("sepet_yemek_id")
    val cartId: Int,
    @SerializedName("yemek_adi")
    val name: String,
    @SerializedName("yemek_resim_adi")
    val imageUrl: String,
    @SerializedName("yemek_fiyat")
    val price: Int,
    @SerializedName("yemek_siparis_adet")
    val quantity: Int,
    @SerializedName("kullanici_adi")
    val username: String
) : Parcelable 