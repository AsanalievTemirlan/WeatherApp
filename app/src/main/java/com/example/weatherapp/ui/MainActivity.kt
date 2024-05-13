package com.example.weatherapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SearchView
import androidx.activity.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var city: String = ""
    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val currentDate = viewModel.date()
        val currentDayOfWeek1 = viewModel.dayName(System.currentTimeMillis())
        binding.searchBtn.setOnClickListener {
            viewModel.getWeather(binding.search.text.toString()).observe(this) { mainWeather ->
                mainWeather.list.let { list ->
                    list.forEach { weatherItem ->
                        binding.tvTemp.text = "${weatherItem.main.temp}"
                        binding.tvHumidity.text = "${weatherItem.main.humidity} %"
                        binding.tvMaxTemp.text = "${weatherItem.main.temp_max}°C"
                        binding.tvSea.text = "${weatherItem.main.pressure} hPa"
                        binding.tvMinTemp.text = "${weatherItem.main.temp_min}°C"
                        binding.tvWindSpeed.text = "${weatherItem.wind.speed} m/s"
                        binding.tvSunRise.text = "${weatherItem.sys.sunrise}"
                        binding.tvSunSet.text = "${weatherItem.sys.sunset}"
                        binding.tvDate.text = currentDate
                        binding.tvDay2.text = currentDayOfWeek1

                        val weather = weatherItem.weather.firstOrNull()
                        if (weather != null) {
                            binding.tvWeather.text = weather.main
                            changeImages(weather.description)
                        }

                        val currentTime = System.currentTimeMillis() / 1000
                        val currentDayOfWeek =
                            Calendar.getInstance().apply { timeInMillis = currentTime * 1000 }
                                .get(Calendar.DAY_OF_WEEK)

                        binding.tvDay.text = when (currentDayOfWeek) {
                            Calendar.MONDAY -> "MONDAY"
                            Calendar.TUESDAY -> "TUESDAY"
                            Calendar.WEDNESDAY -> "WEDNESDAY"
                            Calendar.THURSDAY -> "THURSDAY"
                            Calendar.FRIDAY -> "FRIDAY"
                            Calendar.SATURDAY -> "SATURDAY"
                            Calendar.SUNDAY -> "SUNDAY"
                            else -> "Unknown"
                        }

                    }
                }
            }
        }
    }
}


private fun changeImages(conditions: String): Pair<Int, Int> {
    return when (conditions) {
        "Clear Sky", "Sunny", "Clear" -> Pair(R.drawable.img_4, R.drawable.back_sun)
        "Partly Cloudy", "Clouds", "Overcast", "Mist", "Forge" -> Pair(
            R.drawable.img_1,
            R.drawable.colin
        )

        "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain" -> Pair(
            R.drawable.cloud,
            R.drawable.severin
        )

        "Light Snow", "Moderate Snow", "heavy Snow", "Blizzard" -> Pair(
            R.drawable.img_2,
            R.drawable.back_snow
        )

        else -> Pair(R.drawable.img_4, R.drawable.back_sun)
    }
}



