package me.mfathy.airlinesbook.injection.modules

import android.content.Context
import dagger.Binds
import dagger.Module
import me.mfathy.airlinesbook.AirlinesApplication


/**
 * Dagger application module to provide app context.
 */
@Module
abstract class ApplicationModule {
    @Binds
    abstract fun bindContext(application: AirlinesApplication): Context

}
