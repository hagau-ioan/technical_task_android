package com.sliidepoc.ui

import com.sliidepoc.domain.api.data.OAuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Unit testing should work only with mock data, fast execution and also because we are using HILT and because we cannot
 * use this feature here we need to instantiate all the references and DI references manually based on the Interfaces exposed
 * by any object expectation from app.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DataStoreTest {

    private var closeable: AutoCloseable? = null

    @Mock
    var OAuthRepository: OAuthRepository? = null

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
    }

    @After
    fun after() {
        closeable?.close()
    }

    @Test
    fun test_saveTC_success() = runTest {
        OAuthRepository?.let {

            val expectedValue = true

            `when`(OAuthRepository?.saveTC(anyLong())).thenReturn(flow { emit(expectedValue) })

            var result = false
            it.saveTC(1L).take(1).collect { saved: Boolean ->
                result = saved
            }

            assertEquals(result, expectedValue)

        } ?: AssertionError("LocalRepository mock instance cannot be created.")
    }

    @Test
    fun test_isSavedTC_success() = runTest {
        OAuthRepository?.let {

            val expectedValue = 1L

            `when`(OAuthRepository?.isTCSaved()).thenReturn(flow { emit(expectedValue) })

            var result = -1L
            it.isTCSaved().take(1).collect { saved: Long ->
                result = saved
            }

            assertEquals(result, expectedValue)

        } ?: AssertionError("LocalRepository mock instance cannot be created.")
    }
}