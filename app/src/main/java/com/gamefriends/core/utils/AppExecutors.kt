package com.gamefriends.core.utils


import android.os.Handler
import android.os.Looper
import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executors
import java.util.concurrent.Executor
import javax.inject.Inject


class AppExecutors @VisibleForTesting constructor(
    private val diskIo : Executor,
    private  val networkIo: Executor,
    private val mainThread: Executor
) {

    companion object {
        private const val THREAD_COUNT = 3
    }

    @Inject
    constructor(): this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(THREAD_COUNT),
        MainThreadExecutors()
    )

    fun diskIO(): Executor = diskIo

    fun networkIO(): Executor = networkIo

    fun mainThread(): Executor = mainThread


    private class MainThreadExecutors: Executor {
        private val mainThreadHandlers = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandlers.post(command)
        }
    }
}