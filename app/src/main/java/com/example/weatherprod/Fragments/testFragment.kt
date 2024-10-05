//package com.example.weatherprod
//
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.navigation.navGraphViewModels
//import com.example.weatherprod.Adapter.FourDayAdapter
//import com.example.weatherprod.MyViewModel
//import com.example.weatherprod.R
//import com.example.weatherprod.WeatherState
//import com.example.weatherprod.databinding.FragmentForecastBinding
//import com.google.android.gms.common.api.Status
//import com.google.android.libraries.places.api.Places
//import com.google.android.libraries.places.api.model.Place
//import com.google.android.libraries.places.widget.Autocomplete
//import com.google.android.libraries.places.widget.AutocompleteActivity
//import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
//
//
//class Forecast : Fragment() {
//
//    private var binding: FragmentForecastBinding? = null
//
//    private val viewModel by navGraphViewModels<MyViewModel>(R.id.nav_graph)
//    private var adapter = FourDayAdapter()
//    private lateinit var autocompleteLauncher: ActivityResultLauncher<Intent>
//    private var cityName = "Baku"
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding = FragmentForecastBinding.inflate(layoutInflater, container, false)
//        return binding?.root
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        if (!Places.isInitialized()) {
//            Places.initialize(
//                requireContext(),
//                getString(R.string.google_maps_key)  // Инициализируем Places API, если он ещё не был инициализирован
//            )
//        }
//        binding?.swiper?.setOnRefreshListener {
//            viewModel.getWeather(city = cityName)
//            binding?.swiper?.isRefreshing = false
//        }
//
//        autocompleteLauncher =
//            registerForActivityResult(    // Регистрация ActivityResultLauncher для получения результата выбора места
//                ActivityResultContracts.StartActivityForResult()
//            ) { result ->
//
//                if (result.resultCode == Activity.RESULT_OK) { // Обработка результата выбора места
//                    val place: Place =
//                        Autocomplete.getPlaceFromIntent(result.data!!)  // Получаем данные о выбранном месте из интента
//
//                    cityName = place.name   // Отображаем название выбранного места в TextView
//                    binding?.tvLocation?.text = cityName
//                    viewModel.getWeather(cityName ?: "")
//                } else if (result.resultCode == AutocompleteActivity.RESULT_ERROR) { // Можно передать координаты места в ViewModel для дальнейшего использования
//                }
//            }
//
//
//        binding?.locationIV?.setOnClickListener {
//
//            val fields = listOf(
//                Place.Field.ID,
//                Place.Field.NAME,
//                Place.Field.LAT_LNG
//            )  // Определяем нужные поля для автозаполнения
//
//            val intent = Autocomplete.IntentBuilder(
//                AutocompleteActivityMode.OVERLAY,
//                fields
//            )    // Создаем Intent для автозаполнения
//                .build(requireContext())
//
//            autocompleteLauncher.launch(intent) // Запускаем активность автозаполнения
//        }
//
//
//        viewModel.getWeather(city = cityName)
//        binding?.recyclerView?.adapter = adapter
//
//
//
//
//        viewModel.weather.observe(viewLifecycleOwner) { weather ->
//            weather?.let {
//                val formattedDate = viewModel.formatDate(weather.location.localtime ?: "")
//                val getIcon = viewModel.getCurrentIconByCode(
//                    weather.current.condition.code,
//                    weather.current.isDay
//                )
//                val setCity = viewModel.setPhotoByName(weather.location.name, weather.current.isDay)
//
//                binding?.mainFragment?.setBackgroundResource(setCity)
//                binding?.tvTemp?.text = weather.current.temperature.toInt().toString()
//                binding?.tvHumidity?.text = weather.current.humidity.toString()
//                binding?.tvDate?.text = formattedDate
//                binding?.tvTime?.text = weather.current.lastUpdated
//                binding?.tvFeelsLike?.text = weather.current.feelsLike.toString()
//                binding?.tvLocation?.text = weather.location.name
//                binding?.windSpeed?.text = weather.current.windKph.toString()
//                binding?.tvCondition?.text = weather.current.condition.text
//                binding?.myIcon?.setImageResource(getIcon)
//                binding?.progressBar?.visibility = View.GONE
//
//            }
//
//            viewModel.weeklyWeatherState.observe(viewLifecycleOwner) {
//                adapter.addData(it.orEmpty())
//
//            }
//
//        }
//    }
//
//}