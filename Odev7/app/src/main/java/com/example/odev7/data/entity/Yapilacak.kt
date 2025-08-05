package com.example.odev7.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "yapilacaklar")
data class Yapilacak(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String
) : Serializable 