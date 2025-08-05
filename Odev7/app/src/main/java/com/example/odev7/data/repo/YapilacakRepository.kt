package com.example.odev7.data.repo

import com.example.odev7.data.dao.YapilacakDao
import com.example.odev7.data.entity.Yapilacak
import javax.inject.Inject

class YapilacakRepository @Inject constructor(private val ydao: YapilacakDao) {
    
    suspend fun yapilacaklariYukle(): List<Yapilacak> = ydao.yapilacaklariYukle()
    
    suspend fun kaydet(yapilacak: Yapilacak) = ydao.kaydet(yapilacak)
    
    suspend fun guncelle(yapilacak: Yapilacak) = ydao.guncelle(yapilacak)
    
    suspend fun sil(yapilacak: Yapilacak) = ydao.sil(yapilacak)
    
    suspend fun ara(aramaKelimesi: String): List<Yapilacak> = ydao.ara(aramaKelimesi)
} 