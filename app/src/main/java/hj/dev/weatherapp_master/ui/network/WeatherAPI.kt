package hj.dev.weatherapp_master.ui.network

import hj.dev.weatherapp_master.ui.backend.LocationDetailWeatherRes
import hj.dev.weatherapp_master.ui.backend.LocationRes
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherAPI {
    @GET("location/search")  // get city woeid
    fun searchLocation(@Query("query") city: String): Single<List<LocationRes>>

    @GET("location/{woeid}")
    fun getLocationInfo(@Path("woeid") woeid: Int): Single<LocationDetailWeatherRes>

}