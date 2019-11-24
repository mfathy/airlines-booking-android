package me.mfathy.airlinesbook.data.preference

import me.mfathy.airlinesbook.data.model.AccessTokenEntity

/**
 * Created by Mohammed Fathy on 22/12/2018.
 * dev.mfathy@gmail.com
 *
 * PreferenceHelper Contract to read/write access token.
 */
interface PreferenceHelper {
    fun getAccessToken(): AccessTokenEntity
    fun setAccessToken(accessToken: AccessTokenEntity)
}