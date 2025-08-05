package com.example.odev7.data.dao

import androidx.room.*
import com.example.odev7.data.entity.Yapilacak

@Dao
interface YapilacakDao {
    @Query("SELECT * FROM yapilacaklar")
    suspend fun yapilacaklariYukle(): List<Yapilacak>
    
    @Insert
    suspend fun kaydet(yapilacak: Yapilacak)
    
    @Update
    suspend fun guncelle(yapilacak: Yapilacak)
    
    @Delete
    suspend fun sil(yapilacak: Yapilacak)
    
    @Query("SELECT * FROM yapilacaklar WHERE name like '%' || :aramaKelimesi || '%'")
    suspend fun ara(aramaKelimesi: String): List<Yapilacak>
} 