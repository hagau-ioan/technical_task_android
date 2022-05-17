package com.sliidepoc.data.http.adapter

import android.content.Context
import com.sliidepoc.common.retrofit.adapter.BaseRetrofitServiceAdapter
import com.sliidepoc.data.http.UrlConfig
import com.sliidepoc.data.http.api.ApiInterface
import com.sliidepoc.data.http.oauth.SessionManager
import com.sliidepoc.stats.Stats
import com.sliidepoc.stats.StatsEventsNetwork
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Retrofit Service types declarations and management of these. These are required to expose the
 * final ApiInterface with url requests for Retrofit and OkHttp
 * Nothing else beside Retrofit Service Adapter access domain declaration should be add it here.
 *
 * @author Ioan Hagau
 * @since 2020.11.26
 */
@Singleton
open class RetrofitServiceAdapter
@Inject constructor(
    @ApplicationContext context: Context,
    private val stats: Stats,
    private val sessionManager: SessionManager
) : BaseRetrofitServiceAdapter(context) {

    val commonApiInterface: ApiInterface by lazy {
        getBasicRetrofitBuilder(UrlConfig.RootDomain.path).build().create(ApiInterface::class.java)
        // Replace all @SerializedName with @Json for moshi json parser
        // getBasicMoshiRetrofitBuilder.build().create(ApiInterface::class.java)
    }

    override fun sendLog(message: String) {
        stats.sendNetworkEvent(StatsEventsNetwork.MessageNetwork(message))
    }

    override fun getToken(): String? {
        return sessionManager.token
    }
}
