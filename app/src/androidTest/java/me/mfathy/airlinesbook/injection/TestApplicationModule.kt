package me.mfathy.airlinesbook.injection

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class TestApplicationModule {

    @Binds
    abstract fun bindContext(application: Application): Context
}