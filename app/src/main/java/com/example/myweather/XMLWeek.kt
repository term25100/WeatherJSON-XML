package com.example.myweather

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myweather.databinding.ActivityJsonWeekBinding
import com.example.myweather.databinding.ActivityXmlweekBinding
import org.json.JSONObject
import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

const val APIKEY2="0d93566244d74bbdb80122858241808"
class XMLWeek : AppCompatActivity() {
    private lateinit var binding: ActivityXmlweekBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityXmlweekBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.getWeatherXML.setOnClickListener {
            getWeatherXML(binding.town.text.toString())
        }

        binding.backmain.setOnClickListener {
            intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun getWeatherXML(town: String){
        val url="https://api.weatherapi.com/v1/current.xml"+
                "?key=$APIKEY2&q=$town&aqi=no"
        val queue= Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET
            ,url,
            {
                    response->
                val documentBuilderFactory = DocumentBuilderFactory.newInstance()
                val documentBuilder = documentBuilderFactory.newDocumentBuilder()
                val document: Document = documentBuilder.parse(InputSource(StringReader(response)))
                val rootElement = document.documentElement

                val feel = rootElement.getElementsByTagName("feelslike_c").item(0).textContent
                val lastUpdated = rootElement.getElementsByTagName("last_updated").item(0).textContent
                val tempC = rootElement.getElementsByTagName("temp_c").item(0).textContent
                val windKph = rootElement.getElementsByTagName("wind_kph").item(0).textContent
                val pressureMb = rootElement.getElementsByTagName("pressure_mb").item(0).textContent

                binding.temperature.text="Температура: "+tempC
                binding.updated.text="Обновлено: "+lastUpdated
                binding.windspeed.text="Скорость ветра: "+windKph+" км/ч"
                binding.feeling.text="Ощущается как: "+feel
                binding.pressure.text="Давление: "+pressureMb+" мм.рт.ст"
                Log.d("Volley","Volley Message: $response")
            },
            {
                Log.d("Volley","Volley Error: $it")
            }
        )
        queue.add(stringRequest)
    }
}