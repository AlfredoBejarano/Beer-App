package me.alfredobejarano.beerapp.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.alfredobejarano.beerapp.BuildConfig
import me.alfredobejarano.beerapp.datasource.local.converters.FoodPairingConverter
import me.alfredobejarano.beerapp.model.local.Beer

@TypeConverters(FoodPairingConverter::class)
@Database(entities = [Beer::class], version = BuildConfig.DATABASE_VERSION, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun provideBeerDao(): BeerDao
}