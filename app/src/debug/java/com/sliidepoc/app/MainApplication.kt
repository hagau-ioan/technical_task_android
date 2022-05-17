package com.sliidepoc.app

import androidx.hilt.work.HiltWorkerFactory
import com.sliidepoc.app.general.LeakCanaryUtils
import com.sliidepoc.common.utils.ProcessUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.01.13
 */
@HiltAndroidApp
class MainApplication : BaseApplication() {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        // Do some custom things related to the current main process.
        if (ProcessUtils.isMainProcess(this)) {
            // Working with leakCanary
            LeakCanaryUtils.configureLeakCanary()
            LeakCanaryUtils.configureLeakCanaryWatcher(this)
        }
    }
}
