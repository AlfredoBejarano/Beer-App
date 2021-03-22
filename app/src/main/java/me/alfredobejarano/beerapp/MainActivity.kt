package me.alfredobejarano.beerapp

import android.R.anim.fade_in
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import me.alfredobejarano.beerapp.R.id.navigation_fragment
import me.alfredobejarano.beerapp.databinding.ActivityMainBinding
import me.alfredobejarano.beerapp.utils.startAnimationCompat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationView.startAnimationCompat(this, fade_in)

        val navController =
            (supportFragmentManager.findFragmentById(navigation_fragment) as NavHostFragment)
                .navController

        binding.navigationView.setupWithNavController(navController)
    }
}