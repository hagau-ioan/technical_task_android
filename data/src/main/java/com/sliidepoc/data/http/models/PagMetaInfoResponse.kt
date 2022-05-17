@file:Suppress("unused")

package com.sliidepoc.data.http.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Concrete implementation related to proxy WSP calls
 * This may be a subject of change in case we change the WSP provider.
 *
 * @author Ioan Hagau
 * @since 2020.11.26
 */
@Parcelize
@Keep
data class PagMetaInfoResponse(
    @SerializedName("total")
    val total: Int = 0,

    @SerializedName("pages")
    val pages: Int = 0,

    @SerializedName("page")
    val page: Int = 0,

    @SerializedName("limit")
    val limit: Int = 0
) : Parcelable
