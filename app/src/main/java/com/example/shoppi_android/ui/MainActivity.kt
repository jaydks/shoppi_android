package com.example.shoppi_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shoppi_android.R
import com.example.shoppi_android.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationMain.itemIconTintList = null

        val navController = supportFragmentManager.findFragmentById(R.id.container_main)?.findNavController()
        navController?.let{
            binding.navigationMain.setupWithNavController(it)
        }
    }
}
