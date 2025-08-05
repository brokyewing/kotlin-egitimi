package com.example.odev7.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.odev7.data.entity.Yapilacak
import com.example.odev7.data.repo.YapilacakRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KayitViewModel @Inject constructor(private val yrepo: YapilacakRepository) : ViewModel() {
    fun kaydet(yapilacakAd: String) {
        CoroutineScope(Dispatchers.Main).launch {
            yrepo.kaydet(Yapilacak(0, yapilacakAd))
        }
    }
} 