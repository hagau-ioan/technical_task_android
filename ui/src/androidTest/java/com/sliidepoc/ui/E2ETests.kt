package com.sliidepoc.ui

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.sliidepoc.common.utils.CoroutineContextThread
import com.sliidepoc.common.utils.formater.StringUtils
import com.sliidepoc.domain.api.data.UsersRepository
import com.sliidepoc.domain.usecases.http.AddUserUseCase
import com.sliidepoc.domain.usecases.http.DeleteUserUseCase
import com.sliidepoc.domain.usecases.http.GetUsersFromLastPageUseCase
import com.sliidepoc.stats.Stats
import com.sliidepoc.ui.activities.MainActivity
import com.sliidepoc.ui.components.DrawContentScreenState
import com.sliidepoc.ui.features.users.DisplayUsersScreen
import com.sliidepoc.ui.features.users.UsersViewModel
import com.sliidepoc.ui.themes.SliidepocTheme
import com.sliidepoc.ui.utils.Constants.TEST_TAG_USERS_LIST
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import javax.inject.Inject

/**
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class E2ETests {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var stats: Stats

    @Inject
    lateinit var coroutineContextThread: CoroutineContextThread

    @Inject
    lateinit var usersRepository: UsersRepository

    private lateinit var usersViewModel: UsersViewModel

    @Before
    fun before() {
        hiltRule.inject()

        // Because of the @HiltViewModel WE must instantiate ViewModel object
        usersViewModel = UsersViewModel(
            GetUsersFromLastPageUseCase(usersRepository),
            AddUserUseCase(usersRepository),
            DeleteUserUseCase(usersRepository),
            stats,
            coroutineContextThread/*,
            SavedStateHandle().apply {
                set("key", "something")
            }*/
        )
    }

    @After
    fun after() {
    }

    @Test
    fun test_testTheExistanceOfListComponent_success() = runTest {
        composeTestRule.setContent {
            SliidepocTheme {
                DisplayUsersScreen(
                    usersViewModel = usersViewModel,
                    navController = rememberNavController(),
                    DrawContentScreenState(
                        rememberCoroutineScope(),
                        rememberScaffoldState(),
                        remember { mutableStateOf(StringUtils.EMPTY_STRING) },
                        remember { mutableStateOf(0) }
                    )
                )
            }
        }
        composeTestRule.onNodeWithTag(TEST_TAG_USERS_LIST).assertExists()
    }
//    composeTestRule.onRoot().printToLog("TAG")
}
