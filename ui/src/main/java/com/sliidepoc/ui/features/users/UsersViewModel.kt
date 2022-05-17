package com.sliidepoc.ui.features.users

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sliidepoc.common.utils.ext.TAG
import com.sliidepoc.common.utils.ext.withTimeoutError
import com.sliidepoc.domain.api.data.mapper.dto.model.UserDto
import com.sliidepoc.domain.usecases.http.AddUserUseCase
import com.sliidepoc.domain.usecases.http.DeleteUserUseCase
import com.sliidepoc.domain.usecases.http.GetUsersFromLastPageUseCase
import com.sliidepoc.stats.Stats
import com.sliidepoc.stats.StatsEventsLogs
import com.sliidepoc.ui.utils.DataLoadingStates
import com.sliidepoc.ui.utils.ViewModelLiFeCycle
import com.sliidepoc.common.utils.CoroutineContextThread
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * @author Hagău Ioan
 * @since 2022.01.21
 */
@Suppress("unused")
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersFromLastPageUseCase: GetUsersFromLastPageUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val stats: Stats,
    private val coroutineContextThread: CoroutineContextThread,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), ViewModelLiFeCycle {

    private val _users = MutableStateFlow(emptyList<UserDto>())
    val users: StateFlow<List<UserDto>> = _users

    private val _dataState = MutableStateFlow(DataLoadingStates.START)
    val dataState: StateFlow<DataLoadingStates> = _dataState

    fun loadUsers() {
        release()
        viewModelScope.launch(coroutineContextThread.io) {
            Log.d("StepStep", "loadUsers")
            _dataState.emit(DataLoadingStates.LOADING)
            _users.emit(emptyList())
            withTimeoutError(run = { scope ->
                scope.launch {
                    getUsersFromLastPageUseCase.invoke().catch {
                        _dataState.emit(DataLoadingStates.ERROR)
                        _users.emit(emptyList())
                    }.collect {
                        _dataState.emit(DataLoadingStates.SUCCESS)
                        _users.emit(it)
                    }
                }
            }, error = { ex, scope ->
                scope.launch {
                    _dataState.emit(DataLoadingStates.ERROR)
                    _users.emit(emptyList())
                    stats.sendLogEvent(StatsEventsLogs.E(TAG, ex.toString()))
                    release()
                }
            })
        }
    }

    fun addUser(name: String, email: String) {
        viewModelScope.launch(coroutineContextThread.io) {
            addUserUseCase(name, email).collect {
                if (it) {
                    // TODO: we will load every time the users after adding a NEW user.
                    // TODO: But the list update should be updated only following an item insertion
                    //  so not the full list.
                    loadUsers()
                }
            }
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch(coroutineContextThread.io) {
            deleteUserUseCase(userId).collect {
                if (it) {
                    // TODO: we will load every time the users after adding a NEW user.
                    // TODO: But the list update should be updated only following an item insertion
                    //  so not the full list.
                    loadUsers()
                }
            }
        }
    }

    override fun onStop() {
        release()
    }

    private fun release() {
    }
}
