@file:Suppress("BlockingMethodInNonBlockingContext")

package com.sliidepoc.ui

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import androidx.work.testing.TestListenableWorkerBuilder
import com.sliidepoc.common.utils.CoroutineContextThread
import com.sliidepoc.domain.api.data.OAuthRepository
import com.sliidepoc.stats.Stats
import com.sliidepoc.ui.services.foreground.ForegroundServiceSample
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runners.MethodSorters
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ExperimentalCoroutinesApi

class WorkManagerServiceTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var OAuthRepository: OAuthRepository

    @Inject
    lateinit var stats: Stats

    @Inject
    lateinit var coroutineContextThread: CoroutineContextThread

    private lateinit var workerFactory: WorkerFactory

    private var context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun before() {

        hiltRule.inject()

        Dispatchers.setMain(coroutineContextThread.io)

        workerFactory = object : WorkerFactory() {
            override fun createWorker(
                appContext: Context,
                workerClassName: String,
                workerParameters: WorkerParameters
            ): ListenableWorker {
                return ForegroundServiceSample(
                    appContext, workerParameters, OAuthRepository, stats, coroutineContextThread
                )
            }
        }
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_foregroundServiceDoWork_success() = runTest {
        val worker = TestListenableWorkerBuilder<ForegroundServiceSample>(
            context = context
        ).setWorkerFactory(workerFactory).build()
        val result = worker.doWork()
        assertThat(result, `is`(ListenableWorker.Result.success()))
    }
}
