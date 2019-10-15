package com.example.core.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import coil.DefaultRequestOptions
import coil.ImageLoader
import coil.request.GetRequest
import coil.request.LoadRequest
import coil.request.RequestDisposable

/**
 * Test image loader for Coil, it will not attempt to load the real image in instrumentation tests,
 * instead it will load randomly generated color drawables.
 * Source: https://coil-kt.github.io/coil/image_loaders/#testing
 */
class TestImageLoader : ImageLoader {

    private fun getDrawable(): ColorDrawable {
        return listOf(
            ColorDrawable(Color.CYAN),
            ColorDrawable(Color.BLUE),
            ColorDrawable(Color.BLACK),
            ColorDrawable(Color.GRAY),
            ColorDrawable(Color.GREEN),
            ColorDrawable(Color.MAGENTA),
            ColorDrawable(Color.RED),
            ColorDrawable(Color.YELLOW)
        ).random()
    }

    private val disposable = object : RequestDisposable {
        override fun isDisposed() = true
        override fun dispose() {
            // No-op
        }
    }

    override val defaults = DefaultRequestOptions()

    override fun load(request: LoadRequest): RequestDisposable {
        // Always call onStart before onSuccess.
        val drawable = getDrawable()
        request.target?.onStart(drawable)
        request.target?.onSuccess(drawable)
        return disposable
    }

    override suspend fun get(request: GetRequest) = getDrawable()

    override fun clearMemory() {
        // No-op
    }

    override fun shutdown() {
        // No-op
    }
}
