package com.example.myweather

import android.app.DownloadManager.Request
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myweather.databinding.ActivityJsonWeekBinding
import com.example.myweather.databinding.ActivityMainBinding
import com.google.gson.JsonObject
import org.json.JSONObject

const val APIKEY="0d93566244d74bbdb80122858241808"
class JsonWeek : AppCompatActivity() {
    private lateinit var binding: ActivityJsonWeekBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityJsonWeekBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.getWeatherJson.setOnClickListener {
            getWeatherJson(binding.town.text.toString())
        }

        binding.backmain.setOnClickListener {
            intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun getWeatherJson(town: String){
        val url="https://api.weatherapi.com/v1/current.json"+
                "?key=$APIKEY&q=$town&aqi=no"
        val queue=Volley.newRequestQueue(this)
        val stringRequest =StringRequest(com.android.volley.Request.Method.GET
        ,url,
            {
                response->
                val obj = JSONObject(response)
                val temp= obj.getJSONObject("current")
                binding.feeling.text="Ощущается как: "+ temp.getString("feelslike_c")
                binding.temperature.text="Температура: "+ temp.getString("temp_c")
                binding.updated.text="Обновлено: "+ temp.getString("last_updated")
                binding.windspeed.text="Скорость ветра: "+ temp.getString("wind_kph")+" км/ч"
                binding.pressure.text="Давление: "+ temp.getString("pressure_mb")+" мм.рт.ст"

                Log.d("Volley","Volley Message: $response")
            },
            {
                Log.d("Volley","Volley Error: $it")
            }
        )
        queue.add(stringRequest)
    }
}