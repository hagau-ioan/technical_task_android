package com.sliidepoc.ui

import com.sliidepoc.common.utils.formater.StringUtils
import com.sliidepoc.domain.api.data.OAuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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
class OAuthTest {

    private var closeable: AutoCloseable? = null

    @Mock
    var oAuthRepository: OAuthRepository? = null

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)
    }

    @After
    fun after() {
        closeable?.close()
    }

    @Test
    fun test_getClientId_success() = runTest {
        oAuthRepository?.let {

            val expectedValue = "client_id"

            `when`(it.getClientId()).thenReturn(flow { emit(expectedValue) })

            var result = StringUtils.EMPTY_STRING
            it.getClientId().take(1).collect { saved: String ->
                result = saved
            }

            assertEquals(result, expectedValue)

        } ?: AssertionError("LocalRepository mock instance cannot be created.")
    }

    @Test
    fun test_getClientSecret_success() = runTest {
        oAuthRepository?.let {

            val expectedValue = "client_secret"

            `when`(it.getClientSecret()).thenReturn(flow { emit(expectedValue) })

            var result = StringUtils.EMPTY_STRING
            it.getClientSecret().take(1).collect { saved: String ->
                result = saved
            }

            assertEquals(result, expectedValue)

        } ?: AssertionError("LocalRepository mock instance cannot be created.")
    }
}
