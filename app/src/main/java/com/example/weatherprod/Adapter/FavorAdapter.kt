package com.example.weatherprod.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weatherprod.Model.ForecastDay
import com.example.weatherprod.Model.WeatherDTO
import com.example.weatherprod.databinding.ViewholderFavorBinding

class FavorAdapter:RecyclerView.Adapter<FavorAdapter.Api2ViewHolder>() {
    private lateinit var context: Context
    private val dataList = mutableListOf<WeatherDTO>()

    fun addData(newList: List<WeatherDTO>) {
        dataList.clear()
        dataList.addAll(newList)
        notifyDataSetChanged()

    }
    inner class Api2ViewHolder(private val binding:ViewholderFavorBinding):
    ViewHolder(binding.root){
        fun onBind (data:WeatherDTO){
            binding.tvTemperature.text = data.current.temperature.toInt().toString()
            binding.tvWind.text = data.current.windKph.toString()
            binding.tvCondition.text = data.current.condition.text
            binding.tvName.text = data.location.name

        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavorAdapter.Api2ViewHolder {
        context = parent.context
        val binding = ViewholderFavorBinding.inflate(LayoutInflater.from(context),parent,false)
        return Api2ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: FavorAdapter.Api2ViewHolder, position: Int) {
        holder.onBind(dataList[position])
    }
}