package com.sliidepoc.data.http.api

/**
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
sealed class HttpResponseCodes(val code: Int) {
    object ResourceNotExist : HttpResponseCodes(404)
    object ResourceCreated : HttpResponseCodes(201)
    object ResourceDeleted : HttpResponseCodes(204)
}
