package hj.dev.weatherapp_master.ui.backend

import com.google.gson.annotations.SerializedName

data class LocationDetailWeatherRes(
    @SerializedName("consolidated_weather")
    val weatherList: List<ConsolidatedWeather>,
    val title: String
) {
    data class ConsolidatedWeather(
        @SerializedName("weather_state_name")
        val weatherName: String,
        @SerializedName("weather_state_abbr")
        val weatherImg: String,
        @SerializedName("the_temp")
        val temp: Double,
        val humidity: Int
    )
}