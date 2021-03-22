package me.alfredobejarano.beerapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import me.alfredobejarano.beerapp.utils.updateNetworkInfo

class NetworkBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context.updateNetworkInfo()
    }
}