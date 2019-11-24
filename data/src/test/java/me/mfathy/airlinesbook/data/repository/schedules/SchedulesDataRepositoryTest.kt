package me.mfathy.airlinesbook.data.repository.schedules

import io.reactivex.Flowable
import me.mfathy.airlinesbook.data.store.AirportsDataStore
import me.mfathy.airlinesbook.data.store.AirportsDataStoreFactory
import me.mfathy.airlinesbook.data.store.local.AirportsCache
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy on 17/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for AirportsDataRepository
 */
@RunWith(MockitoJUnitRunner::class)
class SchedulesDataRepositoryTest {

    private val mockStore = mock(AirportsDataStore::class.java)
    private val mockFactory = mock(AirportsDataStoreFactory::class.java)
    private val repository = SchedulesDataRepository(mockFactory)

    @Before
    fun setup() {
        stubFactoryGetDataStore()
    }

    @Test
    fun testGetFlightSchedulesCompletes() {
        stubFactoryGetFlightSchedules()
        val testObserver = repository.getFlightSchedules("CAI", "RUH", "2019-01-01", 1, 1).test()
        testObserver.assertComplete()
    }

    private fun stubFactoryGetDataStore() {
        `when`(mockFactory.getDataStore(anyBoolean(), anyBoolean())).thenReturn(mockStore)
    }

    private fun stubFactoryGetFlightSchedules() {
        `when`(mockStore.getFlightSchedules(
                anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyInt()
        )).thenReturn(Flowable.just(listOf()))
    }
}
