package com.sliidepoc.data.http.oauth

import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
@Singleton
class SessionManager @Inject constructor() {
    // This token will expire from time to time, we need a logic to see if the token is
    // valid or not, around a failure request.
    var token: String? = null
    var credentials: Pair<String?, String?>? = null
}
