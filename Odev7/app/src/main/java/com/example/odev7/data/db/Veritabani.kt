package com.example.odev7.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.odev7.data.dao.YapilacakDao
import com.example.odev7.data.entity.Yapilacak

@Database(entities = [Yapilacak::class], version = 1)
abstract class Veritabani : RoomDatabase() {
    abstract fun yapilacakDao(): YapilacakDao
} 