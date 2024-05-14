package com.example.weatherapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bishkek()
        binding.searchBtn.setOnClickListener {
            viewModel.getWeather(binding.search.text.toString()).observe(this) {
                with(binding) {
                    tvTemp.text = "${it.main.temp}°C"
                    tvMaxTemp.text = "Max: ${it.main.temp_max}°C"
                    tvMinTemp.text = "Min: ${it.main.temp_min}°C"
                    fellLike.text = "Fells like: ${it.main.feels_like}°C"
                    tvWeather.text = it.weather[0].main
                    tvDay.text = it.weather[0].description
                    weatherIcon.setImageResource(changeImages(it.weather[0].main).first)
                    layout.setBackgroundResource(changeImages(it.weather[0].main).second)
                }

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bishkek() {
        viewModel.getWeather("Bishkek").observe(this) {
            with(binding) {
                tvTemp.text = "${it.main.temp}°C"
                tvMaxTemp.text = "Max: ${it.main.temp_max}°C"
                tvMinTemp.text = "Min: ${it.main.temp_min}°C"
                fellLike.text = "Fells like: ${it.main.feels_like}°C"
                tvWeather.text = it.weather[0].main
                tvDay.text = it.weather[0].description
                weatherIcon.setImageResource(changeImages(it.weather[0].main).first)
                layout.setBackgroundResource(changeImages(it.weather[0].main).second)
            }

        }
    }


    private fun changeImages(conditions: String): Pair<Int, Int> {
        return when (conditions) {
            "Mist", "Smoke", "Haze", "Dust", "Fog", "Sand", "Ash", "Squall", "Tornado", "Clouds" -> Pair(
                R.drawable.img_1,
                R.drawable.colin
            )

            "Clear" -> Pair(R.drawable.img_4, R.drawable.back_sun)

            "Rain" -> Pair(R.drawable.cloud, R.drawable.severin)

            "Snow" -> Pair(R.drawable.img_2, R.drawable.back_snow)

            else -> Pair(R.drawable.img_4, R.drawable.back_sun)
        }
    }
}



