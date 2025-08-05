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
class DetayViewModel @Inject constructor(private val yrepo: YapilacakRepository) : ViewModel() {
    fun guncelle(yapilacak: Yapilacak) {
        CoroutineScope(Dispatchers.Main).launch {
            yrepo.guncelle(yapilacak)
        }
    }
} 