package com.sliidepoc.data.http.api

/**
 * Expose any work with Backend. Working with Retrofit, HTTP OK, etc ...
 *
 * @author Ioan Hagau
 * @since 2020.11.23
 */
interface OAuthProxy {

    suspend fun loadCredentials(clientId: String, clientSecret: String): Pair<String, String>?

    suspend fun loadToken(login: String, password: String): String?
}
