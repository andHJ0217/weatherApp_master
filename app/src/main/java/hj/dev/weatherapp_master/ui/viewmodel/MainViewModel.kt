package hj.dev.weatherapp_master.ui.viewmodel
import kotlin.math.roundToInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hj.dev.weatherapp_master.ui.backend.LocationDetailWeatherRes
import hj.dev.weatherapp_master.ui.backend.LocationRes
import hj.dev.weatherapp_master.ui.data.*
import hj.dev.weatherapp_master.ui.network.WeatherAPI
import hj.dev.weatherapp_master.ui.util.ImageUtil
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainViewModel(private val weatherRequest: WeatherAPI) : BaseViewModel() {


    private val bRefresh = MutableLiveData<Boolean>()
    private val weatherData = MutableLiveData<MutableList<WeatherData>>()
    val refreshinglayout: LiveData<Boolean> get() = bRefresh
    val weatherDataViewList: LiveData<MutableList<WeatherData>> get() = weatherData
    init {
        reqLocationSearch(true)
    }

    /**
     * 지역검색 API 호출
     * @param showProgress progress 사용여부 (true:Progress View, false:SwipeRefreshLayout)
     */
    fun reqLocationSearch(showProgress: Boolean) {
        if (showProgress) {
            isProgress.value = true
            reqLocationSearchAPI()
        } else {
            isProgress.value?.let {
                if (it) { // isProgress == true
                    bRefresh.value = false
                } else {//isProgress == false
                    weatherData.value = null // Refresh 중 RecyclerView 초기화
                    reqLocationSearchAPI()
                }
            }
        }
    }



    fun <T> createRetrofit(cls: Class<T>, baseUrl: String): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()
            .create(cls)
    }
    private fun createOkHttpClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()

        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(interceptor)
        return builder.build()
    }
    private fun reqLocationSearchAPI() {
        addDisposable(
            weatherRequest.searchLocation(LOCATION_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ searchResponse ->
                    reqLocationAPI(searchResponse)
                }, {
                    throwable.value = it
                    cancelLoading()
                })
        )
    }

    /**
     * 날씨검색 API 호출
     */
    private fun reqLocationAPI(searchResponse: List<LocationRes>) {
        val weatherDataList: MutableList<WeatherData> = mutableListOf() // 날씨정보 리스트
        val singleList: MutableList<Single<LocationDetailWeatherRes>> = mutableListOf() // 검색한 지역 리스트
        searchResponse.map {
            singleList.add(weatherRequest.getLocationInfo(it.woeid))
        }

        addDisposable(
            Single.concat(singleList)
                .filter { response ->
                    2 <= response.weatherList.size
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    with(WeatherData()) {
                        this.localTitle = response.title
                        for (i in 0 until 2) {
                            if (i == 0) {
                                this.run {
                                    this.todayHumidity = "${response.weatherList[i].humidity}%"
                                    this.todayTemp =
                                        "${response.weatherList[i].temp.roundToInt()}℃"
                                    this.todayWaterName = response.weatherList[i].weatherName
                                    this.todayImageUrl  = ImageUtil.getImage(
                                        response.weatherList[i].weatherImg)
                                }
                            } else {
                                this.run {
                                    this.tomorrowHumidity = "${response.weatherList[i].humidity}%"
                                    this.tomorrowTemp =
                                        "${response.weatherList[i].temp.roundToInt()}℃"
                                    this.tomorrowWaterName = response.weatherList[i].weatherName
                                    this.tomorrowImageUrl = ImageUtil.getImage(
                                        response.weatherList[i].weatherImg)

                                }
                            }
                        }
                        weatherDataList.add(this)
                        // 검색한 지역 수와 날씨정보 결과의 수가 같으면 실행
                        if (singleList.size == weatherDataList.size) {
                            weatherData.value = weatherDataList
                            cancelLoading()
                        }
                    }

                }, {
                    throwable.value = it
                    cancelLoading()
                })
        )
    }


    private fun cancelLoading() {
        isProgress.value?.let {
            if (it) {
                isProgress.value = false
            } else {
                bRefresh.value = false
            }
        }
    }
}