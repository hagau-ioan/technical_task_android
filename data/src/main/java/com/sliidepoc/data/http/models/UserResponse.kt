package com.sliidepoc.data.http.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Concrete implementation entity which expose data received from WSPProxy calls for an entity.
 * The methods here should not be changes even we change the WSP provider. These methods expose
 * through implemented interface the required methods on UI level.
 *
 * @author Ioan Hagau
 * @since 2020.11.26
 */
@Parcelize
@Keep
data class UserResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("status")
    val status: String
) : Parcelable

