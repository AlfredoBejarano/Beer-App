package me.alfredobejarano.beerapp.utils

import android.content.Context
import android.net.ConnectivityManager
import me.alfredobejarano.beerapp.AppState

fun Context?.updateNetworkInfo() {
    val activeNetworkInfo =
        (this?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.activeNetworkInfo
    AppState.isConnected = activeNetworkInfo?.isConnected == true
}