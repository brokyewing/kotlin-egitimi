package com.brokyfood.app.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

interface UserProvider {
    val username: String
}

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext context: Context
) : UserProvider {
    private val prefs = context.getSharedPreferences("broky_session_prefs", Context.MODE_PRIVATE)

    override val username: String by lazy {
        val existing = prefs.getString(KEY_USERNAME, null)
        if (!existing.isNullOrBlank()) {
            existing
        } else {
            val generated = "broky_" + UUID.randomUUID().toString().replace("-", "").take(10)
            prefs.edit().putString(KEY_USERNAME, generated).apply()
            generated
        }
    }

    companion object {
        private const val KEY_USERNAME = "user_id"
    }
}
