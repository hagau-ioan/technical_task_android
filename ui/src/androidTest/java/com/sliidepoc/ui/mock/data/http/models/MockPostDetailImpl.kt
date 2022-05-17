package com.sliidepoc.ui.mock.data.http.models

import android.os.Parcelable
import com.sliidepoc.common.utils.formater.StringUtils
import com.sliidepoc.domain.api.data.http.models.PostDetail
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
data class MockPostDetailImpl(
    private val id: String? = null,
    private val title: String? = null,
    private val description: String? = null
) : PostDetail, Parcelable {

    override fun getId(): String? {
        return id
    }

    override fun getTitle(): String? {
        return title
    }

    override fun getDescription(): String? {
        return description
    }

    override fun getPrice(): Float {
        return 0f
    }

    override fun getDiscount(): Float {
        return 0f
    }

    override fun getStatus(): Boolean {
        return false
    }

    override fun getImage(): String {
        return StringUtils.EMPTY_STRING
    }
}
