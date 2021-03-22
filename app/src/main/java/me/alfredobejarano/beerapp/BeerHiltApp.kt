package me.alfredobejarano.beerapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import me.alfredobejarano.beerapp.utils.updateNetworkInfo

@HiltAndroidApp
class BeerHiltApp : Application() {
    override fun onCreate() {
        super.onCreate()
        updateNetworkInfo()
    }
}