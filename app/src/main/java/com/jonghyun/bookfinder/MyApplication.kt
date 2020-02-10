package com.jonghyun.bookfinder

import android.app.Application
import com.jonghyun.bookfinder.di.koinModules
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(koinModules)
        }
    }

}