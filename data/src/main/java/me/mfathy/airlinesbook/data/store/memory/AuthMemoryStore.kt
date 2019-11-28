package me.mfathy.airlinesbook.data.store.memory

import android.util.LruCache
import io.reactivex.Single
import me.mfathy.airlinesbook.data.mapper.memory.InMemoryTokenMapper
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.store.memory.models.InMemoryToken
import javax.inject.Inject

class AuthMemoryStore @Inject constructor(private val lruCache: LruCache<String, InMemoryToken>,
                                          private val memoryTokenMapper: InMemoryTokenMapper) : MemoryCache {

    override fun get(key: String): Single<AccessTokenEntity> {
        return Single.defer {
            val token = lruCache.get(key)
            if (token != null) {
                val currentTime = System.currentTimeMillis()
                if (currentTime > token.expiresIn) lruCache.remove(key)
                Single.just(memoryTokenMapper.mapToEntity(token))
            } else Single.just(AccessTokenEntity())
        }


    }

    override fun set(key: String, value: AccessTokenEntity) {
        val newValue = value.copy(expiresIn = System.currentTimeMillis().plus(value.expiresIn))
        lruCache.put(key, memoryTokenMapper.mapFromEntity(newValue))
    }


}