package com.example.myweather

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.button2.setOnClickListener {
            intent=Intent(this, JsonWeek::class.java)
            startActivity(intent)
            finish()
        }
        binding.button3.setOnClickListener {
            intent=Intent(this, XMLWeek::class.java)
            startActivity(intent)
            finish()
        }
    }
}