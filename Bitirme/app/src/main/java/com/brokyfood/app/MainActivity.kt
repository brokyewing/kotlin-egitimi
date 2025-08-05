package com.brokyfood.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.brokyfood.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentLanguage: String
        get() = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .getString("current_language", "tr") ?: "tr"
        set(value) = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit()
            .putString("current_language", value)
            .apply()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Make the app fullscreen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Varsayılan başlığı gizle
        setupNavigation()
        
        // Apply saved language
        val appLocale = LocaleListCompat.forLanguageTags(currentLanguage)
        AppCompatDelegate.setApplicationLocales(appLocale)
        
        // Hide system bars
        hideSystemBars()
    }

    private fun hideSystemBars() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    private fun setupNavigation() {
        try {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
            
            navHostFragment?.let { fragment ->
                binding.bottomNav.setupWithNavController(fragment.navController)
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error setting up navigation", e)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        updateLanguageMenuTitle(menu.findItem(R.id.action_language))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_language -> {
                toggleLanguage()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleLanguage() {
        currentLanguage = if (currentLanguage == "tr") "en" else "tr"
        val appLocale = LocaleListCompat.forLanguageTags(currentLanguage)
        AppCompatDelegate.setApplicationLocales(appLocale)
        recreate()
    }

    private fun updateLanguageMenuTitle(menuItem: MenuItem?) {
        menuItem?.title = if (currentLanguage == "tr") "English" else "Türkçe"
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemBars()
        }
    }
} 