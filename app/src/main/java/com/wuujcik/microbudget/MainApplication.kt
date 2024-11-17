package com.wuujcik.microbudget

import android.app.Application
import com.wuujcik.microbudget.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainApplication)
            modules(
                viewModelsModule,
                dataModule,
                dispatcherModule
            )
        }
    }

    companion object {
        private var app: MainApplication? = null
        fun app() = checkNotNull(app) { "App not initialized" }
    }
}
