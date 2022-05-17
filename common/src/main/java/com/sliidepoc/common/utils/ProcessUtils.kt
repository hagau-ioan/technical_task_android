@file:Suppress("unused")

package com.sliidepoc.common.utils

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Process
import android.util.Log
import com.sliidepoc.common.utils.ext.TAG
import com.sliidepoc.common.utils.formater.StringUtils
import java.util.*

object ProcessUtils {

    /**
     * Check whether we are in the main process.
     *
     */
    fun isMainProcess(context: Context): Boolean {
        return try {
            val mainProcess: String = getProcessName(context)
            val currentProcess: String = getCurrentProcessName(context)
            if (currentProcess == StringUtils.EMPTY_STRING) {
                Log.d(TAG, "currentProcess is empty string")
                return true
            }
            mainProcess.isNotEmpty() && currentProcess.isNotEmpty() && currentProcess == mainProcess
        } catch (e: Exception) {
            Log.d(TAG, "Could not get package info for " + context.packageName)
            true
        }
    }

    fun getMyProcessId(): Int {
        return Process.myPid()
    }

    private fun getOtherProcessName(context: Context): String {
        return String.format(Locale.ROOT, "%s:other", getProcessName(context))
    }

    private fun getCurrentProcessName(context: Context): String {
        val myPid = Process.myPid()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var myProcess: RunningAppProcessInfo? = null
        val runningProcesses: List<RunningAppProcessInfo>? = try {
            activityManager.runningAppProcesses
        } catch (exception: SecurityException) {
            Log.d(TAG, "Could not get running app processes", exception)
            return StringUtils.EMPTY_STRING
        }
        if (runningProcesses != null) {
            for (process in runningProcesses) {
                if (process.pid == myPid) {
                    myProcess = process
                    break
                }
            }
        }
        if (myProcess == null) {
            Log.d(TAG, "Could not find running process for $myPid")
            return StringUtils.EMPTY_STRING
        }
        return myProcess.processName
    }

    private fun getProcessName(context: Context): String {
        val packageManager = context.packageManager
        val packageInfo: PackageInfo = try {
            packageManager.getPackageInfo(context.packageName, PackageManager.GET_SERVICES)
        } catch (e: java.lang.Exception) {
            Log.d(TAG, "Could not get package info for " + context.packageName)
            return StringUtils.EMPTY_STRING
        }
        return packageInfo.applicationInfo.processName
    }
}
