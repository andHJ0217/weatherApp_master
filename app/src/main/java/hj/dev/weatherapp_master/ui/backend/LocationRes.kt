package hj.dev.weatherapp_master.ui.backend

import com.google.gson.annotations.SerializedName

data class LocationRes(  @SerializedName("woeid")
                         var woeid: Int,
                         @SerializedName("title")
                         var title: String)
