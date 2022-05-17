package com.sliidepoc.data.http.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Concrete implementation entity which expose data received from WSPProxy calls for an entity.
 * The methods here should not be changes even we change the WSP provider. These methods expose
 * through implemented interface the required methods on UI level.
 *
 * @author Ioan Hagau
 * @since 2020.11.26
 */
@Keep
data class UserRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("status")
    val status: String
)
