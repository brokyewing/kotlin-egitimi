package com.example.odev7.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.odev7.data.entity.Yapilacak
import com.example.odev7.data.repo.YapilacakRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnasayfaViewModel @Inject constructor(private val yrepo: YapilacakRepository) : ViewModel() {
    var yapilacaklarListesi = MutableLiveData<List<Yapilacak>>()
    
    init {
        yapilacaklariYukle()
    }
    
    fun yapilacaklariYukle() {
        CoroutineScope(Dispatchers.Main).launch {
            yapilacaklarListesi.value = yrepo.yapilacaklariYukle()
        }
    }
    
    fun ara(aramaKelimesi: String) {
        CoroutineScope(Dispatchers.Main).launch {
            yapilacaklarListesi.value = yrepo.ara(aramaKelimesi)
        }
    }
    
    fun sil(yapilacak: Yapilacak) {
        CoroutineScope(Dispatchers.Main).launch {
            yrepo.sil(yapilacak)
            yapilacaklariYukle()
        }
    }
} 