package me.alfredobejarano.beerapp.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Beers")
data class Beer(
    @ColumnInfo(name = "pk")
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String = "",
    val tagLine: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val liked: Boolean = false,
    val rejected: Boolean = false,
    val foodPairings: List<String> = emptyList()
)