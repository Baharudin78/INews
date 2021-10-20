package com.baharudin.inews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.baharudin.inews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var navigationController : NavController
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigation = supportFragmentManager.findFragmentById(R.id.fragment_view) as NavHostFragment
        navigationController = navigation.findNavController()

        binding.apply {
            bottomNavigationView.setupWithNavController(navigationController)
        }

    }
}