package me.mfathy.airlinesbook

import android.app.Application
import me.mfathy.airlinesbook.injection.DaggerApplicationComponent
import timber.log.Timber

/**
 * Created by Mohammed Fathy on 17/12/2018.
 * dev.mfathy@gmail.com
 * Base Application class >> used to inject dagger modules to use DI.
 */
class AirlinesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupDagger()

    }

    private fun setupDagger() {
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
