package com.wuujcik.microbudget

import android.app.Application
import com.wuujcik.microbudget.di.*
import org.koin.android.BuildConfig//wrong one, but temporarily needs to stay
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MainApplication)
            modules(
                daoModule,
                databaseModule,
                viewModelModule,
                repositoryModule,
                dataManagerModule,
                interactorModule,
            )
        }
    }
}
