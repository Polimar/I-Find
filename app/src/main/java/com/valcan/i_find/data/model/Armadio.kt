package com.valcan.i_find.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "armadi")
data class Armadio(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val posizione: String
) 