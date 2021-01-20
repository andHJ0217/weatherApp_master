package hj.dev.weatherapp_master.ui.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hj.dev.weatherapp_master.R

class WeatherViewData() {
   val localTitle= MutableLiveData<String>()
   val todayWaterName= MutableLiveData<String>()
   val todayTemp= MutableLiveData<String>()
   val todayHumidity= MutableLiveData<String>()
   val todayImageUrl= MutableLiveData<Int>()
   val tomorrowWaterName= MutableLiveData<String>()
   val tomorrowTemp= MutableLiveData<String>()
   val tomorrowHumidity= MutableLiveData<String>()
   val tomorrowImageUrl= MutableLiveData<Int>()


   val localTitleData: LiveData<String> get() = localTitle
   val todayData: LiveData<String> get() = todayWaterName
   val todayTempData: LiveData<String> get() = todayTemp
   val todayHumidityData: LiveData<String> get() = todayHumidity
   val todayImageUrlData: LiveData<Int> get() = todayImageUrl
   val tomorrowWeatherNameData: LiveData<String> get() = tomorrowWaterName
   val tomorrowTempData: LiveData<String> get() = tomorrowTemp
   val tomorrowHumidityData: LiveData<String> get() = tomorrowHumidity
   val tomorrowImageUrlData: LiveData<Int> get() = tomorrowImageUrl

   fun setCityName(cityname: String) {
      localTitle.value = cityname

   }

   fun setTodayWaterName(todayWater: String) {
      todayWaterName.value = todayWater
   }

   fun setTodayTemp(todayTemp_: String) {
      todayTemp.value = todayTemp_
   }

   fun setTodayHumidity(todayHumidity_: String) {
      todayHumidity.value = todayHumidity_
   }

   fun setTodayImageUrl(todayImageUrl_: Int) {
      todayImageUrl.value = todayImageUrl_
   }

   fun setTomorrowWeatherName(tomorrowWeatherName_: String) {
      tomorrowWaterName.value = tomorrowWeatherName_
   }

   fun setTomorrowTemp(tomorrowTemp_: String) {
      tomorrowTemp.value = tomorrowTemp_
   }

   fun setTomorrowHumidity(tomorrowHumidity_: String) {
      tomorrowHumidity.value = tomorrowHumidity_
   }

   fun setTomorrowImageUrl(tomorrowImageUrl_: Int) {
      tomorrowImageUrl.value = tomorrowImageUrl_
   }
}

data class WeatherData(
   var localTitle: String = "",
   var todayWaterName: String = "",
   var todayTemp: String = "",
   var todayHumidity: String = "",
   var todayImageUrl: Int = R.drawable.ic_weather_clear,
   var tomorrowWaterName: String = "",
   var tomorrowTemp: String = "",
   var tomorrowHumidity: String = "",
   var tomorrowImageUrl: Int = R.drawable.ic_weather_clear
)