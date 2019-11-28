package me.mfathy.airlinesbook

import androidx.annotation.NonNull
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import me.mfathy.airlinesbook.data.store.remote.model.RequestHeaders
import me.mfathy.airlinesbook.domain.interactor.token.GetAccessToken
import me.mfathy.airlinesbook.extensions.rx.computation
import me.mfathy.airlinesbook.injection.DaggerApplicationComponent
import javax.inject.Inject


/**
 * Created by Mohammed Fathy on 17/12/2018.
 * dev.mfathy@gmail.com
 * Base Application class >> used to inject dagger modules to use DI.
 */
class AirlinesApplication : DaggerApplication(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    @NonNull
    lateinit var getAccessToken: GetAccessToken

    @Inject
    @NonNull
    lateinit var headers: RequestHeaders

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
        initializeApplication()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().application(this).build()
    }

    private fun initializeApplication() {
        val params = GetAccessToken.Params(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, BuildConfig.GRANT_TYPE)
        val token = getAccessToken.execute(params).computation().blockingGet()
        headers.accessToken = token
    }
}

