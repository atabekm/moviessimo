package com.example.moviessimo

import android.app.Application
import com.example.core.network.di.CoreNetwork
import com.example.core.prefs.di.CorePrefs
import com.example.feature.details.di.FeatureDetail
import com.example.feature.list.di.FeatureList
import com.example.moviessimo.di.Navigation
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.fileProperties

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Check koin version 3.2 or later to see if logger level is still needed
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
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
                    FeatureList.module,
                    FeatureDetail.module,
                    Navigation.module
                )
            )
        }
    }
}
