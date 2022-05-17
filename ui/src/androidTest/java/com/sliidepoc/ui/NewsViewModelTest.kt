@file:Suppress("BlockingMethodInNonBlockingContext")

package com.sliidepoc.ui

import androidx.lifecycle.SavedStateHandle
import com.sliidepoc.common.utils.CoroutineContextThread
import com.sliidepoc.domain.api.data.UsersRepository
import com.sliidepoc.domain.usecases.http.GetPostDetailUseCase
import com.sliidepoc.domain.usecases.http.GetPostsUseCase
import com.sliidepoc.stats.Stats
import com.sliidepoc.ui.features.users.UsersViewModel
import com.sliidepoc.ui.utils.DataLoadingStates
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runners.MethodSorters
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ExperimentalCoroutinesApi
class NewsViewModelTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usersRepository: UsersRepository

    @Inject
    lateinit var stats: Stats

    @Inject
    lateinit var coroutineContextThread: CoroutineContextThread

    private lateinit var usersViewModel: UsersViewModel

    private val savedStateHandle = SavedStateHandle().apply {
        set("key", "something")
    }

    @Before
    fun before() {
        hiltRule.inject()

        Dispatchers.setMain(coroutineContextThread.io)

        // Because of the @HiltViewModel WE must instantiate ViewModel object
        usersViewModel = UsersViewModel(
            GetPostDetailUseCase(usersRepository),
            GetPostsUseCase(usersRepository), stats, coroutineContextThread, savedStateHandle
        )
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_initialLoadingValueIsStart_success() = runTest {
        assertEquals(DataLoadingStates.START, usersViewModel.getDataState().value)
    }

    @Test
    fun test_finishedLoadingValueIsSuccess_success() = runTest {
        val states = mutableListOf<DataLoadingStates>()
        usersViewModel.loadUsers()
        usersViewModel.getDataState().take(3).toList(states)
        assertEquals(states.contains(DataLoadingStates.SUCCESS), true)
    }

//
//    /**
//     * Another brute way to handle a hot flow which will emit different values in time.
//     * This is not ok because we are using delay, but is just for POC concept.
//     */
//    @Test
//    fun test_finishedLoadingValueIsSuccess_success() = runTest {
//        val states = mutableListOf<DataLoadingStates>()
//
//        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
//            newsViewModel.getDataState().collect {
//                states.add(it)
//            }
//        }
//        newsViewModel.loadPosts()
//
//        Thread.sleep(1000)
//        job.cancel()
//        assertEquals(states.contains(DataLoadingStates.SUCCESS), true)
//    }

    @Test
    fun test_countPosts_biggerThan0() = runTest {
        usersViewModel.loadUsers()
        val posts = usersViewModel.getPosts().take(1).toList()
        assertEquals(true, posts.isNotEmpty())
    }

    @Test
    fun test_postContainAllDetails_success() = runTest {
        usersViewModel.loadPostDetails(1)
        val postDetail = usersViewModel.getPostDetails().take(2).toList()
        assertEquals(true, postDetail[1]?.getTitle() != null)
    }
}
