package com.sliidepoc.data.http

import android.content.Context
import com.sliidepoc.data.http.adapter.RetrofitServiceAdapter
import com.sliidepoc.data.http.api.OAuthProxy
import com.sliidepoc.stats.Stats
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * @author Ioan Hagau
 * @since 2020.11.23
 */
@Suppress("unused")
@Singleton
class OAuthProxyImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val retrofitService: RetrofitServiceAdapter,
    private val stats: Stats
) : OAuthProxy {

    override suspend fun loadCredentials(
        clientId: String,
        clientSecret: String
    ): Pair<String, String>? {
        return Pair("user_name", "password")
    }

    override suspend fun loadToken(login: String, password: String): String? {
        return "7b89db2aa73f3d2a7b79a0f6e51b408044fd6db9f82fdca54d427a166bf89fe3"
    }
}
