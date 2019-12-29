package com.example.core.utils.scheduler

import io.reactivex.Scheduler

class TestSchedulers : Schedulers {
    override fun main(): Scheduler = io.reactivex.schedulers.Schedulers.trampoline()

    override fun io(): Scheduler = io.reactivex.schedulers.Schedulers.trampoline()
}
