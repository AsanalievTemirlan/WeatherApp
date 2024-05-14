package com.example.weatherapp.data


data class WeatherModel(
    val main: Main,
    val weather: List<Weather>
)

//data class WeatherInfo(
//    val main: Main,
//    val weatherList: List<Weather>
//)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Weather(
    val main: String,
    val description: String
)

