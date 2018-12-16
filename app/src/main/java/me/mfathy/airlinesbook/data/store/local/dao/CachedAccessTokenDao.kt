package me.mfathy.airlinesbook.data.store.local.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.mfathy.airlinesbook.data.store.local.models.CachedAccessToken

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * Access token data access layer for caching the remote API access token.
 * Every access token has an expiry duration that will expire after spending this duration.
 */
@Dao
abstract class CachedAccessTokenDao {

    @Query("SELECT * FROM access_token WHERE client_id = :clientId")
    abstract fun getCachedAccessToken(clientId: String): Single<CachedAccessToken>

    @Query("SELECT * FROM access_token")
    abstract fun getAllCachedAccessToken(): Flowable<CachedAccessToken>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAccessToken(accessToken: CachedAccessToken): Completable

    @Query("DELETE FROM access_token")
    abstract fun deleteAccessTokens()

}