package me.mfathy.airlinesbook.data.store.remote.utils

/**
 * Created by Mohammed Fathy on 21/12/2018.
 * dev.mfathy@gmail.com
 *
 * NetworkUtils is a network checker helper class for checking if the device is connected to the internet.
 */
interface NetworkUtils {
    fun hasConnection(): Boolean
}