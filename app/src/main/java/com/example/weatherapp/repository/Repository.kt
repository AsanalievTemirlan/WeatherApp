package com.example.weatherapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.WeatherApiService
import com.example.weatherapp.data.WeatherModel
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class Repository @Inject constructor(private val api: WeatherApiService) {

    fun getWeather(city: String): LiveData<WeatherModel> {
        val data = MutableLiveData<WeatherModel>()
        api.getWeather(city = city).enqueue(object : Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.d("apiqq", "Success")
                    data.postValue(response.body())
                } else {
                    Log.d("apiqq", "Unsuccessful response")
                    // В случае неуспешного ответа, вы можете передать пустой объект WeatherModel
                    // или выполнить другую логику обработки ошибки
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.e("ololo", "Error fetching weather data", t)
                // При возникновении ошибки также передайте пустой объект WeatherModel
            }
        })
        return data
    }
}
