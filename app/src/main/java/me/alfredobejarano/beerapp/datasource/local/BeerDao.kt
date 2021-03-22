package me.alfredobejarano.beerapp.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.alfredobejarano.beerapp.model.local.Beer

@Dao
interface BeerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrUpdate(beer: Beer)

    @Query("SELECT * FROM Beers ORDER BY pk ASC")
    suspend fun getBeers(): List<Beer>?

    @Query("SELECT * FROM Beers WHERE liked = 1 ORDER BY pk ASC")
    suspend fun getLikedBeers(): List<Beer>?

    @Query("SELECT * FROM Beers WHERE rejected = 1 ORDER BY pk ASC")
    suspend fun getRejectedBeers(): List<Beer>?
}