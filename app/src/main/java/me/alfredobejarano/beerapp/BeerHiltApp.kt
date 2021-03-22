package me.alfredobejarano.beerapp

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp
import me.alfredobejarano.beerapp.utils.updateNetworkInfo

@HiltAndroidApp
class BeerHiltApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        updateNetworkInfo()
    }
}