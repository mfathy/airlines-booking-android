package me.mfathy.airlinesbook.data.store.remote.utils

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

/**
 * Created by Mohammed Fathy on 22/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for NetworkUtilsImpl
 */
@RunWith(JUnit4::class)
class NetworkUtilsImplTest {

    private val context = Mockito.mock<Context>(Context::class.java)
    private val connManager = Mockito.mock(ConnectivityManager::class.java)
    private val networkInfo = Mockito.mock(NetworkInfo::class.java)
    private val packageManager = Mockito.mock(PackageManager::class.java)
    private val utils = NetworkUtilsImpl(context)

    @Test
    fun testHasConnectionReturnTrue() {
        stubHasConnection()
        assertTrue(utils.hasConnection())
    }

    private fun stubHasConnection() {
        whenever(context.packageManager).thenReturn(packageManager)
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connManager)
        whenever(connManager.activeNetworkInfo).thenReturn(networkInfo)
        whenever(networkInfo.isConnected).thenReturn(true)
    }
}