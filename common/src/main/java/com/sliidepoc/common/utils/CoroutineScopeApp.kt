package com.steelcase.common.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 *
 * @author Hagău Ioan
 * @since 2022.02.23
 */
interface CoroutineScopeApp {
    fun launch(block: suspend CoroutineScope.() -> Unit): Job
}
