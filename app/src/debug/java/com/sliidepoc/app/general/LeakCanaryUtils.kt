@file:Suppress("DEPRECATION")

package com.sliidepoc.app.general

import android.app.Application
import leakcanary.AppWatcher
import leakcanary.AppWatcher.manualInstall
import leakcanary.LeakCanary

/**
 * Handle some settings related to Leak Canary.
 * Upload data related to crashes to a server:
 * @see http://square.github.io/leakcanary/recipes/  --> OnHeapAnalyzedListener for upload
 * @author Ioan Hagau
 * @since 2021.06.16
 */
object LeakCanaryUtils {

    private val isEnable: Boolean = false

    fun configureLeakCanary() {
        LeakCanary.config = LeakCanary.config.copy(
            dumpHeap = isEnable,
            retainedVisibleThreshold = 5,
            maxStoredHeapDumps = 10
        )
        LeakCanary.showLeakDisplayActivityLauncherIcon(isEnable)
    }

    fun configureLeakCanaryWatcher(app: Application) {
        val watchersToInstall = AppWatcher.appDefaultWatchers(app) { watchedObject, description ->
            // Skip some fragments from Leak Canary if required.
            // if (watchedObject !is ......Fragment) {
            AppWatcher.objectWatcher.expectWeaklyReachable(watchedObject, description)
            // }
        }
        if (!AppWatcher.isInstalled) {
            manualInstall(
                application = app,
                watchersToInstall = watchersToInstall
            )
        }
        if (!isEnable) {
//            AppWatcher.objectWatcher.clearWatchedObjects()
//            watchersToInstall.filter { item ->
//                item !is ActivityWatcher
//                        && item !is FragmentAndViewModelWatcher
//                        && item !is RootViewWatcher
//                        && item !is ServiceWatcher
//            }
            AppWatcher.config = AppWatcher.config.copy(
                watchActivities = false,
                watchFragments = false,
                watchViewModels = false,
                watchFragmentViews = false
            )
        } else {
            AppWatcher.config = AppWatcher.config.copy(
                watchActivities = true,
                watchFragments = true,
                watchViewModels = true,
                watchFragmentViews = true
            )
        }
    }
}
