package me.mfathy.airlinesbook.data.mapper.cache

import me.mfathy.airlinesbook.data.mapper.EntityMapper
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.store.local.models.CachedAccessToken
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 */
class AccessTokenEntityCacheMapper @Inject constructor(): EntityMapper<AccessTokenEntity, CachedAccessToken> {
    override fun mapFromEntity(entity: AccessTokenEntity): CachedAccessToken {
        return CachedAccessToken(
                entity.clintId,
                entity.accessToken,
                entity.tokenType,
                entity.expiresIn
        )
    }

    override fun mapToEntity(domain: CachedAccessToken): AccessTokenEntity {
        return AccessTokenEntity(
                domain.clientId,
                domain.accessToken,
                domain.tokenType,
                domain.expiresIn
        )
    }
}