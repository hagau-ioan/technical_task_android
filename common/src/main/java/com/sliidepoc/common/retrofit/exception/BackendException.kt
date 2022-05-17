package com.sliidepoc.common.retrofit.exception

import java.io.IOException

/**
 * Custom SWP backend exception specific to this app.
 *
 * @author Ioan Hagau
 * @since 2020.11.26
 */
class BackendException(private var code: Int = 0, msg: String) : IOException(msg) {
    override val message: String
        get() {
            return "Code Error: " + code.toString() + "\nMessage: " + super.message
        }
}
