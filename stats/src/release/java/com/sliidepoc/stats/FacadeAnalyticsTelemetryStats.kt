package com.sliidepoc.stats

import android.content.Context

/**
 * Firebase Env. to handle CrashLytics and Analytics.
 *
 * @author Ioan Hagău
 * @since 2021.06.16
 */
object FacadeAnalyticsTelemetryStats {

    fun initialize(context: Context) {
        initFirebaseAnalytics(context)
//        initFlurryAnalytics(context)
//        initMixpanelAnalytics(context)
    }

    // --------------------------- Firebase services -----------------------------------------

//    fun getFirebaseAnalytics(context: Context): FirebaseAnalytics {
//    }

    private fun initFirebaseAnalytics(context: Context) {
        // Firebase Services
    }

//    fun getMixpanelAnalytics(@Suppress("UNUSED_PARAMETER") context: Context) {
//    }
//
//    private fun initMixpanelAnalytics(@Suppress("UNUSED_PARAMETER") context: Context) {
//    }
//
//    fun getFlurryAnalytics(@Suppress("UNUSED_PARAMETER") context: Context) {
//    }
//
//    private fun initFlurryAnalytics(@Suppress("UNUSED_PARAMETER") context: Context) {
//    }
//
//    fun getSegmentAnalytics(@Suppress("UNUSED_PARAMETER") context: Context) {
//    }
//
//    private fun initSegmentAnalytics(@Suppress("UNUSED_PARAMETER") context: Context) {
//    }
}
