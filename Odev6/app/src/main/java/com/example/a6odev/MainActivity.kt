package com.example.a6odev

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.WindowCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Remove status bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        
        setContentView(R.layout.activity_main)

        // Initialize Bottom Navigation
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Handle home navigation
                    true
                }
                R.id.navigation_search -> {
                    // Handle search navigation
                    true
                }
                R.id.navigation_live -> {
                    // Handle live navigation
                    true
                }
                R.id.navigation_downloads -> {
                    // Handle downloads navigation
                    true
                }
                R.id.navigation_profile -> {
                    // Handle profile navigation
                    true
                }
                else -> false
            }
        }

        // Initialize SearchView
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Notify fragment about search query
                val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
                if (currentFragment is AnasayfaFragment) {
                    currentFragment.filterMovies(newText)
                }
                return true
            }
        })
    }
}