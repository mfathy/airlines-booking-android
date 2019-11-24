package me.mfathy.airlinesbook.data.store.remote.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 20/12/2018.
 * dev.mfathy@gmail.com
 *
 * NetworkUtils is a network checker helper class for checking if the device is connected to the internet.
 */
class NetworkUtilsImpl @Inject constructor(private val context: Context) : NetworkUtils {

    /**
     * Use this method to check for the internet connection on an android device.
     * @return true if there is an internet connection, false otherwise.
     */
    override fun hasConnection(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}