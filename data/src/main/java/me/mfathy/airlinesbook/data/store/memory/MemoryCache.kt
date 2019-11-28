package me.mfathy.airlinesbook.data.store.memory

import io.reactivex.Single
import me.mfathy.airlinesbook.data.model.AccessTokenEntity

interface MemoryCache {
    fun get(key: String): Single<AccessTokenEntity>
    fun set(key: String, value: AccessTokenEntity)
}