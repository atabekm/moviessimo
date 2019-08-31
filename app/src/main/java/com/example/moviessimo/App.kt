package com.example.moviessimo

import android.app.Application
import com.example.core.network.di.CoreNetwork
import com.example.core.prefs.di.CorePrefs
import com.example.feature.list.di.FeatureList
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(CoreNetwork.module, CorePrefs.module, FeatureList.module))
        }
    }
}
