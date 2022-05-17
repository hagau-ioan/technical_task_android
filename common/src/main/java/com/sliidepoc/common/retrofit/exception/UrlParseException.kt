package com.sliidepoc.common.retrofit.exception

import java.io.IOException

/**
 * Custom SWP backend exception specific to this app.
 *
 * @author Ioan Hagau
 * @since 2020.11.26
 */
class UrlParseException(message: String?) : IOException(message)
