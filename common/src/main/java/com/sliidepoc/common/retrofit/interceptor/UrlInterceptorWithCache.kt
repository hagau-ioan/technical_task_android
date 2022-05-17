package com.sliidepoc.common.retrofit.interceptor

import android.content.Context
import com.sliidepoc.common.retrofit.adapter.BaseRetrofitServiceAdapter
import com.sliidepoc.common.utils.formater.StringUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * One custom type of interceptor used with Retrofit and OKHTTP.
 * This will apply a cache time to all requests.
 * Ex: we want to get some requests from cache for a specific amount of time and not to harm the WSP server
 * with repetitive requests, which will slow down the server but also our waiting response time.
 * This should be done only for cases when we know that the data will not change very soon.
 *
 * @author Ioan Hagau
 * @since 2020.11.26
 */
class UrlInterceptorWithCache(
    override val context: Context,
    private val cachedHours: Int,
    override val callback: BaseRetrofitServiceAdapter.UrlResponseStatus? = null
) :
    UrlInterceptor(
        context,
        callback,
        { StringUtils.EMPTY_STRING }
    ) {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return super.interceptWithCache(chain, cachedHours)
    }
}
