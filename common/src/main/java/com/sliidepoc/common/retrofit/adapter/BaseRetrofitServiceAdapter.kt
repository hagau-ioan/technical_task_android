package com.sliidepoc.common.retrofit.adapter

import android.content.Context
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sliidepoc.common.retrofit.interceptor.UrlInterceptor
import com.sliidepoc.common.retrofit.interceptor.UrlInterceptorWithCache
import com.sliidepoc.common.utils.formater.StringUtils
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manage and build the base types of httpOK requests. Also here we can define some general interceptors
 * for send/receive requests with a WSP server.
 *
 * @author Ioan Hagau
 * @since 2020.11.26
 */
@Singleton
open class BaseRetrofitServiceAdapter @Inject constructor(val context: Context) {

    private val gson: Gson = GsonBuilder().create()

    private val moshi: Moshi = Moshi.Builder().build()

    private lateinit var okHttpClient: OkHttpClient

    private val cookieJar: ClearableCookieJar
// Library is evolution of Stetho ideas, it has standalone client application, and plugins architecture
//    private val networkFlipperPlugin: NetworkFlipperPlugin

    init {
        cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
        // Check for custom interceptor flipper: https://fbflipper.com/docs/setup/plugins/network/
        // Library is evolution of Stetho ideas, it has standalone client application, and plugins architecture
//        networkFlipperPlugin = NetworkFlipperPlugin()
    }

    protected open fun getOkHttpClientWithCache(cachedHours: Int): OkHttpClient {
        if (!this::okHttpClient.isInitialized) {
            okHttpClient = getBaseOkHttpClientBuilder()
//                .addNetworkInterceptor(StethoInterceptor())
//                .addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))
                // --> https://github.com/ChuckerTeam/chucker to handle http GET/POST/PUT/DEL requests.
                // .addInterceptor(ChuckerInterceptor(context))
                .addInterceptor(
                    UrlInterceptorWithCache(
                        context, cachedHours,
                        object : UrlResponseStatus {
                            override fun onSuccess() {
                                urlCallSuccess()
                            }

                            override fun onError() {
                                urlCallError()
                            }
                        }
                    )
                ).build()
        }
        return okHttpClient
    }

    protected open fun getBaseOkHttpClient(): OkHttpClient {
        if (!this::okHttpClient.isInitialized) {
            okHttpClient = getBaseOkHttpClientBuilder()
//                    .addNetworkInterceptor(StethoInterceptor())
//                    .addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))
                .addInterceptor(
                    UrlInterceptor(
                        context,
                        object : UrlResponseStatus {
                            override fun onSuccess() {
                                urlCallSuccess()
                            }

                            override fun onError() {
                                urlCallError()
                            }
                        }
                    ) { getToken() ?: StringUtils.EMPTY_STRING }
                ).build()
        }
        return okHttpClient
    }

    protected open fun getBaseOkHttpClientBuilder(): OkHttpClient.Builder {
        val intLogging = HttpLoggingInterceptor(fun(message: String) {
            sendLog(message)
        })
        intLogging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(intLogging)
            .connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS).cookieJar(cookieJar).retryOnConnectionFailure(true)
    }

    protected open fun getBasicRetrofitBuilder(rootDomain: String = UrlInterceptor.RETROFIT_DUMMY_URL): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(rootDomain)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getBaseOkHttpClient())
    }

    protected open fun getBasicMoshiRetrofitBuilder(rootDomain: String = UrlInterceptor.RETROFIT_DUMMY_URL): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(rootDomain)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(getBaseOkHttpClient())
    }

    protected open fun sendLog(message: String) {}

    protected open fun urlCallSuccess() {}

    protected open fun urlCallError() {}

    protected open fun getToken(): String? {
        return StringUtils.EMPTY_STRING
    }

    protected open fun refreshToken(): String? {
        return StringUtils.EMPTY_STRING
    }

    interface UrlResponseStatus {

        fun onSuccess()

        fun onError()
    }
}
