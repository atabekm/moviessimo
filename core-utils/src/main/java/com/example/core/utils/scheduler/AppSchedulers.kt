package com.example.core.utils.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class AppSchedulers : Schedulers {
    override fun main(): Scheduler = AndroidSchedulers.mainThread()

    override fun io(): Scheduler = io.reactivex.schedulers.Schedulers.io()
}
