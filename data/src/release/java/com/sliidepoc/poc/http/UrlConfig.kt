package com.sliidepoc.poc.http

/**
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
sealed class UrlConfig(val path: String) {
    object RootDomain : UrlConfig("https://gorest.co.in/public/v1/")
    // https://gorest.co.in/public-api/ - normal url to be used for production
    // TODO: for release the entities used to handle HTTP responses must be adapted, could be another
    // API retrofit file. The json structure for public version is little bit different.
}