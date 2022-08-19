package com.example.moviessimo

import android.app.Application
import com.example.core.network.di.CoreNetwork
import com.example.core.prefs.di.CorePrefs
import com.example.core.utils.di.CoreUtils
import com.example.feature.details.di.FeatureDetail
import com.example.feature.list.di.FeatureList
import com.example.moviessimo.di.Navigation
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.fileProperties

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            properties(
                mapOf(
                    "is_debug" to BuildConfig.DEBUG.toString(),
                    "api_key" to BuildConfig.API_KEY
                )
            )
            fileProperties()
            modules(
                listOf(
                    CoreNetwork.module,
                    CorePrefs.module,
                    CoreUtils.module,
                    FeatureList.module,
                    FeatureDetail.module,
                    Navigation.module
                )
            )
        }
    }
}
