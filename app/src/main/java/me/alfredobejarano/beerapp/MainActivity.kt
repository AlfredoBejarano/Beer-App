package me.alfredobejarano.beerapp

import android.R.anim.fade_in
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.alfredobejarano.beerapp.databinding.ActivityMainBinding
import me.alfredobejarano.beerapp.utils.startAnimationCompat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationView.startAnimationCompat(this, fade_in)
    }
}