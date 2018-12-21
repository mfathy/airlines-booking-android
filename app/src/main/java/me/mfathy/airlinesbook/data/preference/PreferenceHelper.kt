package me.mfathy.airlinesbook.data.preference

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 19/12/2018.
 * dev.mfathy@gmail.com
 */
class PreferenceHelper @Inject constructor(context: Context) {

    private var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getAccessToken(): AccessTokenEntity {
        val tokenString = sharedPreferences.getString(PREF_KEY_ACCESS_TOKEN, "{}")
        return Gson().fromJson(tokenString, AccessTokenEntity::class.java)
    }

    fun setAccessToken(accessToken: AccessTokenEntity) {
        val tokeString = Gson().toJson(accessToken)
        sharedPreferences.edit().putString(PREF_KEY_ACCESS_TOKEN, tokeString).apply()
    }

    companion object {
        const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
    }
}