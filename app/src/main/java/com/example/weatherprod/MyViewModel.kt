package com.example.weatherprod

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherprod.Model.ForecastDay
import com.example.weatherprod.Model.WeatherDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val wService: WService
): ViewModel() {
    val state: MutableLiveData<UIState<WeatherDTO?>> = MutableLiveData()
    val weeklyWeatherState = MutableLiveData<UIState<List<ForecastDay>?>>()

    fun getWeather(city: String) {
        state.value = UIState.Loading(true)
        Log.d("ViewModel", "Запрос погоды для города: $city")

        viewModelScope.launch {
            val result = apiCall { wService.getCurrentWeatherByCity(city) }
            state.value = UIState.Loading(false)
            when (result) {
                is ApiResult.Success -> {
                    Log.d("ViewModel", "success: ")
                    state.value = UIState.Success(result.data)
                }

                is ApiResult.Error -> {
                    Log.d("ViewModel", "error ${result.error?.errorMessage}")
                    state.value = UIState.Error(result.error?.errorCode, result.error?.errorMessage)
                }
            }
            Log.d("ViewModel", "Данные о погоде получены: $state")
        }
    }

    fun getWeatherDaily(city: String, day: Int = 4) {
        state.value = UIState.Loading(true)
        viewModelScope.launch {
            val result = apiCall { wService.getDailyWeather(city) }
            when (result) {
                is ApiResult.Success -> {
                    weeklyWeatherState.value = UIState.Success(result.data?.forecast?.forecastDay)
//
                }

                is ApiResult.Error -> {
                    weeklyWeatherState.value =
                        UIState.Error(result.error?.errorCode, result.error?.errorMessage)
                }
            }
        }
    }
    fun formatDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("MMMM dd", Locale.ENGLISH)
        val date: Date? = inputFormat.parse(date)
        return date?.let { outputFormat.format(it) } ?: "Invalid Date"
    }


    fun setPhotoByName(name: String?, isDay: Int?): Int {
        return when (name) {
            "Paris", "Париж" -> if (isDay == 1) R.drawable.city_paris else R.drawable.city_paris_night
            "Baku", "Баку" -> if (isDay == 1) R.drawable.baku else R.drawable.night_baku
            "Minsk", "Минск" -> if (isDay == 1) R.drawable.city_minsk else R.drawable.city_minsk_night
            "London", "Лондон" -> if (isDay == 1) R.drawable.city_london else R.drawable.city_london_night
            "Dubai", "Дубай" -> if (isDay == 1) R.drawable.city_dubai else R.drawable.city_dubai_night
            "New York", "Нью-Йорк" -> if (isDay == 1) R.drawable.city_newyork else R.drawable.city_newyork_night
            else -> if (isDay == 1) R.drawable.defaultphoto else R.drawable.default_night

        }
    }

    fun getCurrentIconByCode(code: Int?, isDay: Int?): Int {
        return when (code) {

            1000 -> if (isDay == 1) R.drawable.ic_113 else R.drawable.ic_113_night
            1003 -> if (isDay == 1) R.drawable.ic_116 else R.drawable.ic_116_night
            1006 -> if (isDay == 1) R.drawable.ic_119 else R.drawable.ic_119_night
            1009 -> if (isDay == 1) R.drawable.ic_122 else R.drawable.ic_122_night
            1030 -> if (isDay == 1) R.drawable.ic_143 else R.drawable.ic_143_night
            1063 -> if (isDay == 1) R.drawable.ic_176 else R.drawable.ic_176_night
            1066 -> if (isDay == 1) R.drawable.ic_179 else R.drawable.ic_179_night
            1069 -> if (isDay == 1) R.drawable.ic_182 else R.drawable.ic_182_night
            1072 -> if (isDay == 1) R.drawable.ic_185 else R.drawable.ic_185_night
            1087 -> if (isDay == 1) R.drawable.ic_200 else R.drawable.ic_200_night
            1114 -> if (isDay == 1) R.drawable.ic_227 else R.drawable.ic_227_night
            1117 -> if (isDay == 1) R.drawable.ic_230 else R.drawable.ic_230_night
            1135 -> if (isDay == 1) R.drawable.ic_248 else R.drawable.ic_248_night
            1147 -> if (isDay == 1) R.drawable.ic_260 else R.drawable.ic_260_night
            1150 -> if (isDay == 1) R.drawable.ic_263 else R.drawable.ic_263_night
            1153 -> if (isDay == 1) R.drawable.ic_281 else R.drawable.ic_281_night
            1168 -> if (isDay == 1) R.drawable.ic_284 else R.drawable.ic_284_night
            1171 -> if (isDay == 1) R.drawable.ic_293 else R.drawable.ic_293_night
            1180 -> if (isDay == 1) R.drawable.ic_296 else R.drawable.ic_296_night
            1183 -> if (isDay == 1) R.drawable.ic_299 else R.drawable.ic_299_night
            1186 -> if (isDay == 1) R.drawable.ic_302 else R.drawable.ic_302_night
            1189 -> if (isDay == 1) R.drawable.ic_305 else R.drawable.ic_305_night
            1192 -> if (isDay == 1) R.drawable.ic_308 else R.drawable.ic_308_night
            1195 -> if (isDay == 1) R.drawable.ic_311 else R.drawable.ic_311_night
            1198 -> if (isDay == 1) R.drawable.ic_314 else R.drawable.ic_314_night
            1201 -> if (isDay == 1) R.drawable.ic_317 else R.drawable.ic_317_night
            1204 -> if (isDay == 1) R.drawable.ic_320 else R.drawable.ic_320_night
            1207 -> if (isDay == 1) R.drawable.ic_323 else R.drawable.ic_323_night
            1210 -> if (isDay == 1) R.drawable.ic_326 else R.drawable.ic_326_night
            1213 -> if (isDay == 1) R.drawable.ic_329 else R.drawable.ic_329_night
            1216 -> if (isDay == 1) R.drawable.ic_332 else R.drawable.ic_332_night
            1219 -> if (isDay == 1) R.drawable.ic_335 else R.drawable.ic_335_night
            1222 -> if (isDay == 1) R.drawable.ic_338 else R.drawable.ic_338_night
            1237 -> if (isDay == 1) R.drawable.ic_350 else R.drawable.ic_350_night
            1240 -> if (isDay == 1) R.drawable.ic_353 else R.drawable.ic_353_night
            1243 -> if (isDay == 1) R.drawable.ic_356 else R.drawable.ic_356_night
            1246 -> if (isDay == 1) R.drawable.ic_359 else R.drawable.ic_359_night
            1249 -> if (isDay == 1) R.drawable.ic_362 else R.drawable.ic_362_night
            1252 -> if (isDay == 1) R.drawable.ic_365 else R.drawable.ic_365_night
            1255 -> if (isDay == 1) R.drawable.ic_368 else R.drawable.ic_368_night
            1258 -> if (isDay == 1) R.drawable.ic_371 else R.drawable.ic_371_night
            1261 -> if (isDay == 1) R.drawable.ic_374 else R.drawable.ic_374_night
            1264 -> if (isDay == 1) R.drawable.ic_377 else R.drawable.ic_377_night
            1273 -> if (isDay == 1) R.drawable.ic_386 else R.drawable.ic_386_night
            1276 -> if (isDay == 1) R.drawable.ic_389 else R.drawable.ic_389_night
            1279 -> if (isDay == 1) R.drawable.ic_392 else R.drawable.ic_392_night
            1282 -> if (isDay == 1) R.drawable.ic_395 else R.drawable.ic_395_night
            else -> R.drawable.feelslike
        }
    }

}











