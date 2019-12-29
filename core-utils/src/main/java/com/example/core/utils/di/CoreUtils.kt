package com.example.core.utils.di

import com.example.core.utils.scheduler.AppSchedulers
import com.example.core.utils.scheduler.Schedulers
import org.koin.dsl.module

object CoreUtils {
    val module = module {
        single<Schedulers> {
            AppSchedulers()
        }
    }
}
