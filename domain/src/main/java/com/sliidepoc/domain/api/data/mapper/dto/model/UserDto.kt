package com.sliidepoc.domain.api.data.mapper.dto.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 *
 * @author Hagău Ioan
 * @since 2022.01.22
 */
@Parcelize
@Keep
data class UserDto(
    val extId: Int = -1,
    val name: String,
    val email: String?,
    val creationDateTime: String?
) : Parcelable