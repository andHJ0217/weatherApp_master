package hj.dev.weatherapp_master.ui.viewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hj.dev.weatherapp_master.R
import hj.dev.weatherapp_master.databinding.ViewHeaderWeatherItemBinding
import hj.dev.weatherapp_master.databinding.ViewWeatherItemBinding
import hj.dev.weatherapp_master.ui.viewmodel.ui.MainActivity
import hj.dev.weatherapp_master.ui.data.WeatherData
import hj.dev.weatherapp_master.ui.data.WeatherViewData

class WeatherAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val weatherItemList = mutableListOf<WeatherViewData?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder: RecyclerView.ViewHolder
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        when (viewType) {
            R.layout.view_header_weather_item -> holder = LocationHeaderHolder(binding as ViewHeaderWeatherItemBinding)
            else -> holder = LocationHolder(binding as ViewWeatherItemBinding)
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LocationHeaderHolder -> holder.binding.lifecycleOwner = holder.binding.root.context as MainActivity
            is LocationHolder -> {
                holder.binding.weatherItemDB= weatherItemList[position - 1]
                holder.binding.lifecycleOwner = holder.binding.root.context as MainActivity

            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            R.layout.view_header_weather_item
        } else {
            R.layout.view_weather_item
        }
    }
    override fun getItemCount()= if (weatherItemList.size > 0) weatherItemList.size + 1 else 0

    fun addItem(_weatherItemList: List<WeatherViewData>) {
        if (weatherItemList.isNotEmpty()) {
            weatherItemList.clear()
        }

        weatherItemList.addAll(_weatherItemList)
        notifyDataSetChanged()
    }


}
class LocationHeaderHolder(val binding: ViewHeaderWeatherItemBinding) : RecyclerView.ViewHolder(binding.root)

class LocationHolder(val binding: ViewWeatherItemBinding) : RecyclerView.ViewHolder(binding.root)

@BindingAdapter("weatherDataList")
fun setWeatherData(view: RecyclerView, weatherDataList: List<WeatherData>?) {
    val weatherItemListList: MutableList<WeatherViewData> = mutableListOf()
    weatherDataList?.map { weatherData ->
        with(WeatherViewData()) {
            setCityName(weatherData.localTitle)
            setTodayWaterName(weatherData.todayWaterName)
            setTodayTemp(weatherData.todayTemp)
            setTodayHumidity(weatherData.todayHumidity)
            setTodayImageUrl(weatherData.todayImageUrl)
            setTomorrowWeatherName(weatherData.tomorrowWaterName)
            setTomorrowTemp(weatherData.tomorrowTemp)
            setTomorrowHumidity(weatherData.tomorrowHumidity)
            setTomorrowImageUrl(weatherData.tomorrowImageUrl)
            weatherItemListList.add(this)
        }
    }
    with(view) {
        if (adapter == null) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = WeatherAdapter().apply {
                addItem(weatherItemListList)
            }
        } else {
            (adapter as WeatherAdapter).addItem(weatherItemListList)
        }
    }

}

