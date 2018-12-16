package me.mfathy.airlinesbook.injection.modules

import dagger.Binds
import dagger.Module
import me.mfathy.airlinesbook.domain.executor.ExecutionThread

/**
 * Dagger module to provide UI and activities dependencies.
 */
@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(executionThread: ExecutionThread): ExecutionThread
}