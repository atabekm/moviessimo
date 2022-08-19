package com.example.core.utils.image

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import coil.ComponentRegistry
import coil.ImageLoader
import coil.decode.DataSource
import coil.request.DefaultRequestOptions
import coil.request.Disposable
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.request.SuccessResult
import kotlinx.coroutines.CompletableDeferred

/**
 * Test image loader for Coil, it will not attempt to load the real image in instrumentation tests,
 * instead it will load randomly generated color drawables.
 * Source: https://coil-kt.github.io/coil/image_loaders/#testing
 */
class TestImageLoader : ImageLoader {
    override val defaults = DefaultRequestOptions()
    override val components = ComponentRegistry()
    override val memoryCache get() = null
    override val diskCache get() = null

    override fun enqueue(request: ImageRequest): Disposable {
        // Always call onStart before onSuccess.
        request.target?.onStart(request.placeholder)
        val result = getDrawable()
        request.target?.onSuccess(result)
        return object : Disposable {
            override val job = CompletableDeferred(newResult(request, result))
            override val isDisposed get() = true
            override fun dispose() {
                // no-op
            }
        }
    }

    override suspend fun execute(request: ImageRequest): ImageResult {
        return newResult(request, getDrawable())
    }

    private fun newResult(request: ImageRequest, drawable: Drawable): SuccessResult {
        return SuccessResult(
            drawable = drawable,
            request = request,
            dataSource = DataSource.MEMORY_CACHE
        )
    }

    override fun newBuilder() = throw UnsupportedOperationException()

    override fun shutdown() {
        // no-op
    }

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
}
