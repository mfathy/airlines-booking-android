package me.mfathy.airlinesbook.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Mohammed Fathy on 17/09/2018.
 * dev.mfathy@gmail.com
 * ViewModelFactory is a Singleton abstract Factory for constructing ViewModel classes by their defined Map key.
 *
 * The problem? If you want to use this ViewModelFactory with different ViewModels,
 * with different constructor argument it will be obliged to pass all parameters from the ViewModelFactory,
 * the result will be a class not scalable and very difficult to use.
 *
 * The Solution: using Dagger multi-binding to build a Factory workaround for Dagger to provide ViewModelProvider.Factory instances.
 * They will contain all required dependencies for ViewModel creation.
 */
@Singleton
class ViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>>?) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        var creator = creators!![modelClass]
        if (creator == null) {
            for (entry in creators.entries) {
                if (modelClass.isAssignableFrom(entry.key)) {
                    creator = entry.value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("Unknown class: $modelClass")
        }
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

}