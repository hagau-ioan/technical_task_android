package com.sliidepoc.data.http.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Concrete implementation entity which expose data received from WSPProxy calls.
 * The methods here may be a subject of change in case we switch the WSP provider.
 *
 * @author Ioan Hagau
 * @since 2020.11.26
 */
@Parcelize
@Keep
data class UsersListResponse(
    @SerializedName("code")
    val code: Int? = null,

    @SerializedName("meta")
    val metaInfoResponse: MetaInfoResponse? = null,

    @SerializedName("data")
    val data: List<UserResponse>? = null
) : Parcelable {

    fun getNrOfPages(): Int? {
        return metaInfoResponse?.pagResponse?.pages
    }
}

