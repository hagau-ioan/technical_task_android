package com.sliidepoc.common.utils.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withTimeout

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.01.31
 */
suspend fun CoroutineScope.withTimeoutError(
    timeout: Long = 5000,
    run: (scope: CoroutineScope) -> Unit,
    error: (ex: Exception, scope: CoroutineScope) -> Unit
): CoroutineScope {
    try {
        withTimeout(timeout) {
            run(this)
        }
    } catch (ex: Exception) {
        error(ex, this)
    }
    return this
}
