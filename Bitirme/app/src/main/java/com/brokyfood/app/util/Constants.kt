package com.brokyfood.app.util

import com.brokyfood.app.BuildConfig

object Constants {
    val BASE_URL: String = BuildConfig.API_BASE_URL.let {
        if (it.endsWith("/")) it else "$it/"
    }

    fun imageUrl(imageName: String): String = "${BASE_URL}resimler/$imageName"
}
