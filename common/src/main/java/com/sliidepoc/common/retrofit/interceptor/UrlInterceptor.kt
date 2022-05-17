package com.sliidepoc.common.retrofit.interceptor

import android.content.Context
import android.util.Log
import com.sliidepoc.common.retrofit.adapter.BaseRetrofitServiceAdapter
import com.sliidepoc.common.retrofit.exception.BackendException
import com.sliidepoc.common.retrofit.exception.UrlParseException
import com.sliidepoc.common.utils.ext.TAG
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Manage some general interceptors for send/receive requests with WSP, also here we can wrap all
 * request data and response data before arriving to the app consumer
 *
 * @author Ioan Hagau
 * @since 2020.11.26
 */
open class UrlInterceptor(
    open val context: Context,
    open val callback: BaseRetrofitServiceAdapter.UrlResponseStatus? = null,
    open val getToken: () -> String,
) : Interceptor {

    companion object {
        const val RETROFIT_DUMMY_URL = "https://dummy.com"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = prepareRequest(chain).build()
        return prepareResponse(original, chain)
    }

    @Throws(IOException::class)
    protected open fun interceptWithCache(chain: Interceptor.Chain, hours: Int): Response {
        val requestBuilder: Request.Builder = prepareRequest(chain)
        requestBuilder.cacheControl(CacheControl.Builder().maxAge(hours, TimeUnit.HOURS).build())
        val original: Request = requestBuilder.build()
        return prepareResponse(original, chain)
    }

    protected open fun handleErrorCodes(exception: BackendException?) {}

    @Throws(UrlParseException::class)
    private fun prepareRequest(chain: Interceptor.Chain): Request.Builder {
        val token = getToken()
        val original: Request = chain.request()
        val builder = original.newBuilder()
        if (token.isNotEmpty()) {
            builder.addHeader("Authorization", "Bearer $token")
        } else {
            // We ned to recall the token refresh
        }
        val wantedUrl: String = getWantedUrl(original)
        val newUrl = original.url.resolve(wantedUrl)
            ?: throw UrlParseException("URL not well-formed: $wantedUrl")
        return builder //.header("Connection", "close")
            .url(newUrl)
    }

    @Throws(IOException::class)
    private fun prepareResponse(original: Request, chain: Interceptor.Chain): Response {

        return try {
            val response: Response = chain.proceed(original)
            if (response.isSuccessful) {
                if (response.cacheResponse == null) { // the response is not from cache
                    // Inform the app system about the fact that the response not from cache is success
                    callback?.onSuccess()
                }
            } else {
                // Inform the app system about the fact that the response is not successful
                callback?.onError()
                Log.e(
                    TAG,
                    "(1) Could not reach endpoint! URL: " + original.url + " METHOD: " + original.method
                )
            }
            response
        } catch (ex: Exception) {
            if (ex is BackendException) {
                handleErrorCodes(ex)
            }
            Log.e(
                TAG,
                "(2) Could not reach endpoint! URL: " + original.url + " METHOD: " + original.method + " error " + ex.message
            )
            throw ex
        }
    }

    /**
     * We can have the retrofit url specified as <first_segment><second_segment> or http://.....
     * Here we can handle these segments by access them.
     */
    @Throws(UrlParseException::class)
    private fun getWantedUrl(request: Request?): String {
        val url = request!!.url
        // val urlPath: String = url.pathSegments[0]
        return url.toString()
    }
}
