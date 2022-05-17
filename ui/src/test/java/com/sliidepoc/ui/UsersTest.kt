package com.sliidepoc.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sliidepoc.common.utils.CoroutineContextThread
import com.sliidepoc.domain.api.data.UsersRepository
import com.sliidepoc.domain.api.data.mapper.dto.model.UserDto
import com.sliidepoc.domain.usecases.http.AddUserUseCase
import com.sliidepoc.domain.usecases.http.DeleteUserUseCase
import com.sliidepoc.domain.usecases.http.GetUsersFromLastPageUseCase
import com.sliidepoc.stats.Stats
import com.sliidepoc.ui.features.users.UsersViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


/**
 * Unit testing should work only with mock data, fast execution and also because we are using HILT and because we cannot
 * use this feature here we need to instantiate all the references and DI references manually based on the Interfaces exposed
 * by any object expectation from app.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UsersTest {

    private var closeable: AutoCloseable? = null

    private var testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    var stats: Stats? = null

    @Mock
    var userRepository: UsersRepository? = null

    @Captor var captor: ArgumentCaptor<UserDto?> ? = null

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        closeable = MockitoAnnotations.openMocks(this)
    }

    @After
    fun after() {
        closeable?.close()
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun test_getUsers_success() = runTest {
        val coroutineContextThread = CoroutineContextThread(io = testDispatcher)
        val expectedUsers = listOf(
            UserDto(1, "Name 1", "email_1@gmail.com", System.currentTimeMillis().toString()),
            UserDto(2, "Name 2", "email_2@gmail.com", System.currentTimeMillis().toString()),
            UserDto(3, "Name 3", "email_3@gmail.com", System.currentTimeMillis().toString()),
            UserDto(4, "Name 4", "email_4@gmail.com", System.currentTimeMillis().toString()),
            UserDto(5, "Name 5", "email_5@gmail.com", System.currentTimeMillis().toString())
        )
        userRepository?.let {
            `when`(it.getUsersFromLastPage()).thenReturn(expectedUsers)
            val usersViewModel = UsersViewModel(
                GetUsersFromLastPageUseCase(it),
                AddUserUseCase(it),
                DeleteUserUseCase(it),
                stats!!,
                coroutineContextThread,
            )

            usersViewModel.loadUsers()

            var results = emptyList<UserDto>()
            usersViewModel.users.take(1).collect { users ->
                results = users
            }

            assertEquals(results[0], expectedUsers[0])

        } ?: AssertionError("LocalRepository mock instance cannot be created.")
    }

    @Test
    fun test_addUser_success() = runTest {
        val coroutineContextThread = CoroutineContextThread(io = testDispatcher)
        val expectedUsers = listOf(
            UserDto(1, "Name 100", "email_100@gmail.com", System.currentTimeMillis().toString())
        )
        userRepository?.let {
            `when`(it.getUsersFromLastPage()).thenReturn(expectedUsers)
            `when`(it.addUser(expectedUsers[0].name, expectedUsers[0].email!!, "male", "active"))
                .thenReturn(true) // Saying that when the add user is a success

            val usersViewModel = UsersViewModel(
                GetUsersFromLastPageUseCase(it),
                AddUserUseCase(it),
                DeleteUserUseCase(it),
                stats!!,
                coroutineContextThread,
            )

            usersViewModel.addUser(expectedUsers[0].name, expectedUsers[0].email!!)

            var results = emptyList<UserDto>()
            usersViewModel.users.take(1).collect { users ->
                results = users
            }

            assertEquals(results[0], expectedUsers[0])

        } ?: AssertionError("LocalRepository mock instance cannot be created.")
    }

    @Test
    fun test_removeUser_success() = runTest {
        val coroutineContextThread = CoroutineContextThread(io = testDispatcher)
        val expectedUsers = listOf(
            UserDto(1, "Name 100", "email_100@gmail.com", System.currentTimeMillis().toString())
        )
        userRepository?.let {
            `when`(it.getUsersFromLastPage()).thenReturn(expectedUsers)
            `when`(it.deleteUser(expectedUsers[0].extId)).thenReturn(true)
            val usersViewModel = UsersViewModel(
                GetUsersFromLastPageUseCase(it),
                AddUserUseCase(it),
                DeleteUserUseCase(it),
                stats!!,
                coroutineContextThread,
            )

            usersViewModel.deleteUser(expectedUsers[0].extId)

            var results = emptyList<UserDto>()
            usersViewModel.users.take(1).collect { users ->
                results = users
            }

            assertEquals(results[0], expectedUsers[0])

        } ?: AssertionError("LocalRepository mock instance cannot be created.")
    }
}
