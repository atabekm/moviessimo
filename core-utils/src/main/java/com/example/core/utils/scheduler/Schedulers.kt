package com.example.core.utils.scheduler

import io.reactivex.Scheduler

interface Schedulers {
    fun main(): Scheduler
    fun io(): Scheduler
}
