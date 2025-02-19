package com.valcan.i_find.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vestiti",
    foreignKeys = [
        ForeignKey(
            entity = Armadio::class,
            parentColumns = ["id"],
            childColumns = ["armadioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("armadioId")]
)
data class Vestito(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val tipo: String,
    val colore: String,
    val posizione: String,
    val armadioId: Long,
    val photoUri: String? = null
) 