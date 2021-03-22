package me.alfredobejarano.beerapp

import android.R.anim.fade_in
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import me.alfredobejarano.beerapp.R.id.navigation_fragment
import me.alfredobejarano.beerapp.databinding.ActivityMainBinding
import me.alfredobejarano.beerapp.receivers.NetworkBroadcastReceiver
import me.alfredobejarano.beerapp.utils.startAnimationCompat

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var networkStatusReceiver: NetworkBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationView.startAnimationCompat(this, fade_in)

        val navController =
            (supportFragmentManager.findFragmentById(navigation_fragment) as NavHostFragment)
                .navController

        binding.navigationView.setupWithNavController(navController)

        networkStatusReceiver = NetworkBroadcastReceiver()
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkStatusReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        networkStatusReceiver?.run(this@MainActivity::unregisterReceiver)
    }
}