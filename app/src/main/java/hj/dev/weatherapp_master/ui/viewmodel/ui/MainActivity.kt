package hj.dev.weatherapp_master.ui.viewmodel.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import hj.dev.weatherapp_master.R
import hj.dev.weatherapp_master.databinding.ActivityMainBinding
import hj.dev.weatherapp_master.ui.util.appModule
import hj.dev.weatherapp_master.ui.viewmodel.MainViewModel
import hj.dev.weatherapp_master.ui.viewmodel.MainViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : BaseActivity<ActivityMainBinding>(){

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    private val mainViewModelFactory: MainViewModelFactory by inject()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule)
        }

        val mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)
        mainViewModel.showProgress.observe(this, Observer {
            if (it) {
                progressBar.show()
            } else {
                progressBar.hide()
            }
        })
        mainViewModel.addDisposable(
            RxSwipeRefreshLayout.refreshes(swRefresh)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mainViewModel.reqLocationSearch(false)
                }
        )
        viewBinding.mainViewModel = mainViewModel
        viewBinding.lifecycleOwner = this
    }


}