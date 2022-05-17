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
data class MetaInfoResponse(
    @SerializedName("pagination")
    val pagResponse: PagMetaInfoResponse? = null
) : Parcelable