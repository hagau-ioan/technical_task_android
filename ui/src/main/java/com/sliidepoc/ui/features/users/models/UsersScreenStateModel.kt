package com.sliidepoc.ui.features.users.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import com.sliidepoc.domain.api.data.mapper.dto.model.UserDto
import com.sliidepoc.ui.utils.DataLoadingStates

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.01.28
 */
data class UsersScreenStateModel(
    val users: State<List<UserDto>>,
    val dataState: State<DataLoadingStates>,
    val listIndexItem: MutableState<Int>,
    val listOffsetItem: MutableState<Int>
) {
    fun getPosts(): List<UserDto> {
        return users.value
    }

    fun getDataState(): DataLoadingStates {
        return dataState.value
    }

    fun getIndex(): Int {
        return listIndexItem.value
    }

    fun getOffset(): Int {
        return listOffsetItem.value
    }

    fun setIndex(index: Int) {
        listIndexItem.value = index
    }

    fun setOffset(offset: Int) {
        listOffsetItem.value = offset
    }
}
