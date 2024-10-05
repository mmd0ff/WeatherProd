package com.example.weatherprod.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weatherprod.Model.ForecastDay
import com.example.weatherprod.R
import com.example.weatherprod.databinding.Viehholder4dayBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FourDayAdapter : RecyclerView.Adapter<FourDayAdapter.ApiViewHolder>() {

    private lateinit var context: Context
    private val dataList = mutableListOf<ForecastDay>()

    fun addData(newList: List<ForecastDay>) {
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()

    }

    inner class ApiViewHolder(private val binding: Viehholder4dayBinding) :
        ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(data: ForecastDay) {


            binding.tvTemperature.text = data.day.temperatureAv.toInt().toString() + "°"
            binding.tvWindSpeed.text = data.day.windKphMax.toInt().toString() + " km/h"
            val formatteDate = formatDateWithDayOfWeek(data.date)
            binding.tvDay.text = formatteDate


            val condition = data.day.condition.code
            val icon = getWeeklyIconByCode(condition)
            binding.ivWeatherIcon.setImageResource(icon)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiViewHolder {
        context = parent.context
        val binding = Viehholder4dayBinding.inflate(LayoutInflater.from(context), parent, false)
        return ApiViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return dataList.size

    }

    override fun onBindViewHolder(holder: ApiViewHolder, position: Int) {
        holder.onBind(dataList[position])

    }

    private fun formatDateWithDayOfWeek(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("EEE dd", Locale.ENGLISH)
        val date: Date? = inputFormat.parse(dateString)
        return date?.let { outputFormat.format(it) } ?: "Invalid Date"
    }

    fun getWeeklyIconByCode(code: Int?): Int {
        return when (code) {
            1000 -> R.drawable.ic_113
            1003 -> R.drawable.ic_116
            1006 -> R.drawable.ic_119
            1009 -> R.drawable.ic_122
            1030 -> R.drawable.ic_143
            1063 -> R.drawable.ic_176
            1066 -> R.drawable.ic_179
            1069 -> R.drawable.ic_182
            1072 -> R.drawable.ic_185
            1087 -> R.drawable.ic_200
            1114 -> R.drawable.ic_227
            1117 -> R.drawable.ic_230
            1135 -> R.drawable.ic_248
            1147 -> R.drawable.ic_260
            1150 -> R.drawable.ic_263
            1153 -> R.drawable.ic_281
            1168 -> R.drawable.ic_284
            1171 -> R.drawable.ic_293
            1180 -> R.drawable.ic_296
            1183 -> R.drawable.ic_299
            1186 -> R.drawable.ic_302
            1189 -> R.drawable.ic_305
            1192 -> R.drawable.ic_308
            1195 -> R.drawable.ic_311
            1198 -> R.drawable.ic_314
            1201 -> R.drawable.ic_317
            1204 -> R.drawable.ic_320
            1207 -> R.drawable.ic_323
            1210 -> R.drawable.ic_326
            1213 -> R.drawable.ic_329
            1216 -> R.drawable.ic_332
            1219 -> R.drawable.ic_335
            1222 -> R.drawable.ic_338
            1237 -> R.drawable.ic_350
            1240 -> R.drawable.ic_353
            1243 -> R.drawable.ic_356
            1246 -> R.drawable.ic_359
            1249 -> R.drawable.ic_362
            1252 -> R.drawable.ic_365
            1255 -> R.drawable.ic_368
            1258 -> R.drawable.ic_371
            1261 -> R.drawable.ic_374
            1264 -> R.drawable.ic_377
            1273 -> R.drawable.ic_386
            1276 -> R.drawable.ic_389
            1279 -> R.drawable.ic_392
            1282 -> R.drawable.ic_395

            else -> R.drawable.humidityicon // Иконка по умолчанию


        }
    }
}