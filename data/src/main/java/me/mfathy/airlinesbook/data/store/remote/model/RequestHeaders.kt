package me.mfathy.airlinesbook.data.store.remote.model

import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import javax.inject.Inject

class RequestHeaders @Inject constructor() {
    lateinit var accessToken: AccessTokenEntity
}