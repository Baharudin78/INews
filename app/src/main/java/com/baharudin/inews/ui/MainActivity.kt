package com.baharudin.inews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.baharudin.inews.R
import com.baharudin.inews.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navigationController : NavController
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigation = supportFragmentManager.findFragmentById(R.id.fragment_view) as NavHostFragment
        navigation.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.entertainmentFragment, R.id.businessFragment,R.id.scienceFragment,R.id.sportFragment,R.id.technologyFragment,R.id.healthFragment ->
                    binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
        navigationController = navigation.findNavController()

        binding.apply {
            bottomNavigationView.setupWithNavController(navigationController)
        }
        

    }
}