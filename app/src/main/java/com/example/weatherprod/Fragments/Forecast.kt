package com.example.weatherprod.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.example.weatherprod.Adapter.FourDayAdapter

import com.example.weatherprod.MyViewModel
import com.example.weatherprod.R
import com.example.weatherprod.UIState
import com.example.weatherprod.databinding.FragmentForecastBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class Forecast : Fragment(

) {


    private var binding: FragmentForecastBinding? = null
    lateinit var inputDateFormat: SimpleDateFormat
    lateinit var outputDateFormat: SimpleDateFormat

    private val viewModel by navGraphViewModels<MyViewModel>(R.id.nav_graph)
    private var adapter = FourDayAdapter()
    private lateinit var autocompleteLauncher: ActivityResultLauncher<Intent>
    private var cityName = "Baku"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForecastBinding.inflate(layoutInflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!Places.isInitialized()) {
            Places.initialize(
                requireContext(),

                getString(R.string.google_maps_key) // Инициализируем Places API, если он ещё не был инициализирован
            )
        }
        binding?.swiper?.setOnRefreshListener {
            viewModel.getWeather(city = cityName)
            binding?.swiper?.isRefreshing = false
        }

        autocompleteLauncher =
            registerForActivityResult(    // Регистрация ActivityResultLauncher для получения результата выбора места
                ActivityResultContracts.StartActivityForResult()
            ) { result ->

                if (result.resultCode == Activity.RESULT_OK) { // Обработка результата выбора места
                    val place: Place =
                        Autocomplete.getPlaceFromIntent(result.data!!)  // Получаем данные о выбранном месте из интента

                    cityName = place.name   // Отображаем название выбранного места в TextView
                    binding?.tvLocation?.text = cityName
                    viewModel.getWeather(cityName ?: "")
                } else if (result.resultCode == AutocompleteActivity.RESULT_ERROR) { // Можно передать координаты места в ViewModel для дальнейшего использования
                }
            }


        binding?.locationIV?.setOnClickListener {

            val fields = listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )  // Определяем нужные поля для автозаполнения

            val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY,
                fields
            )    // Создаем Intent для автозаполнения
                .build(requireContext())

            autocompleteLauncher.launch(intent) // Запускаем активность автозаполнения
        }

        viewModel.getWeather(city = cityName)
        viewModel.getWeatherDaily(cityName)

        binding?.recyclerView?.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                    Log.d("forecast", "Loading: получаем текущую погоду")
                }

                is UIState.Success -> {
                    Log.d("forecast", "Success: данные успешно получены")

                    binding?.progressBar?.visibility = View.GONE
                    state.data?.let { weather ->
                        val formattedDate = viewModel.formatDate(weather.location.localtime ?: "")
                        val iconRes = viewModel.getCurrentIconByCode(
                            weather.current.condition.code,
                            weather.current.isDay
                        )
                        val backgroundRes =
                            viewModel.setPhotoByName(weather.location.name, weather.current.isDay)
                        Log.d("forecast", "Иконка погоды: $iconRes, Фон города: $backgroundRes")

                        // Устанавливаем фон фрагмента
                        binding?.mainFragment?.setBackgroundResource(backgroundRes)

                        // Заполняем текстовые поля
                        binding?.tvTemp?.text = weather.current.temperature.toInt().toString()
                        binding?.tvHumidity?.text = weather.current.humidity.toString()
                        binding?.tvDate?.text = formattedDate
                        binding?.tvTime?.text = weather.current.lastUpdated
                        binding?.tvFeelsLike?.text = weather.current.feelsLike.toString()
                        binding?.tvLocation?.text = weather.location.name
                        binding?.windSpeed?.text = weather.current.windKph.toString()
                        binding?.tvCondition?.text = weather.current.condition.text

                        // Устанавливаем иконку погоды
                        binding?.myIcon?.setImageResource(iconRes)
                    }
                }

                is UIState.Error -> {
                    binding?.progressBar?.visibility = View.GONE
                    Log.e("forecast", "Ошибка получения данных: ${state.errorMessage}")
                    Toast.makeText(requireContext(), "Ошибка загрузки данных", Toast.LENGTH_SHORT)
                        .show()
                    Log.e("WeatherAPI", "Error: ${state.errorMessage}")
                }
            }
        }




        viewModel.weeklyWeatherState.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Error -> Toast.makeText(
                    requireContext(),
                    "Ошибка загрузки прогноза",
                    Toast.LENGTH_SHORT
                ).show()

                is UIState.Loading -> {}
                is UIState.Success -> {
                    Log.d("forecast", "Загрузка недельного прогноза")
                    adapter.addData(it.data.orEmpty())
                }


            }
        }

    }
}