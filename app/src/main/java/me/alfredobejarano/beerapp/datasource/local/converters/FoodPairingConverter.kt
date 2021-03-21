package me.alfredobejarano.beerapp.datasource.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FoodPairingConverter {
    companion object {
        @TypeConverter
        fun fromList(foodPairing: List<String>): String = Gson().toJson(foodPairing)

        @TypeConverter
        fun toList(foodPairing: String): List<String> =
            Gson().fromJson(foodPairing, object : TypeToken<List<String>>() {}.type)
    }
}