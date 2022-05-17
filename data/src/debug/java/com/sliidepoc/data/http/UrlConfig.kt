package com.sliidepoc.data.http

/**
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
sealed class UrlConfig(val path: String) {
    object RootDomain : UrlConfig("https://gorest.co.in/public/v1/")
}