package me.mfathy.airlinesbook.data.mapper.remote

import me.mfathy.airlinesbook.data.mapper.EntityMapper
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.store.remote.model.AccessToken
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 */
open class AccessTokenEntityNetworkMapper @Inject constructor() : EntityMapper<AccessTokenEntity, AccessToken> {
    override fun mapFromEntity(entity: AccessTokenEntity): AccessToken {
        throw UnsupportedOperationException("Airport is readonly: parsed from remote data store only.")
    }

    override fun mapToEntity(domain: AccessToken): AccessTokenEntity {
        return AccessTokenEntity(
                domain.clientId,
                domain.accessToken,
                domain.tokenType,
                domain.expiresIn
        )
    }

}