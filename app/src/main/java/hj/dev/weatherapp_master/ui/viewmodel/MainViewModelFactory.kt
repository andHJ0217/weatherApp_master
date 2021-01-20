package hj.dev.weatherapp_master.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hj.dev.weatherapp_master.ui.network.WeatherAPI

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val weatherRequest: WeatherAPI) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(weatherRequest) as T
    }
}