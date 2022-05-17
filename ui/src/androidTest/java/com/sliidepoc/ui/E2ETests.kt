package com.sliidepoc.ui

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.rememberNavController
import com.sliidepoc.common.utils.CoroutineContextThread
import com.sliidepoc.common.utils.formater.StringUtils
import com.sliidepoc.domain.api.data.OAuthRepository
import com.sliidepoc.domain.api.data.UsersRepository
import com.sliidepoc.domain.usecases.datastore.SaveClientIdUseCase
import com.sliidepoc.domain.usecases.datastore.SaveClientSecretUseCase
import com.sliidepoc.domain.usecases.http.GetPostDetailUseCase
import com.sliidepoc.domain.usecases.http.GetPostsUseCase
import com.sliidepoc.stats.Stats
import com.sliidepoc.ui.activities.MainActivity
import com.sliidepoc.ui.components.DrawContentScreenState
import com.sliidepoc.ui.features.home.DisplayHomeScreen
import com.sliidepoc.ui.features.home.HomeViewModel
import com.sliidepoc.ui.features.users.DisplayNewsScreen
import com.sliidepoc.ui.features.users.UsersViewModel
import com.sliidepoc.ui.themes.SliidepocTheme
import com.sliidepoc.ui.utils.Constants.TEST_TAG_NEWS_LIST
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
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
    lateinit var OAuthRepository: OAuthRepository

    @Inject
    lateinit var usersRepository: UsersRepository

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var usersViewModel: UsersViewModel

    @Before
    fun before() {
        hiltRule.inject()

        val coroutineContextThread = CoroutineContextThread(io = Dispatchers.IO)
        // Because of the @HiltViewModel WE must instantiate ViewModel object
        homeViewModel = HomeViewModel(
            SaveClientSecretUseCase(OAuthRepository),
            SaveClientIdUseCase(OAuthRepository),
            stats,
            coroutineContextThread
        )
        usersViewModel = UsersViewModel(
            GetPostDetailUseCase(usersRepository),
            GetPostsUseCase(usersRepository),
            stats,
            coroutineContextThread,
            SavedStateHandle().apply {
                set("key", "something")
            }
        )
    }

    @After
    fun after() {
    }

    @Test
    fun test_recomposeLayoutBasedOnNewEmitValue_success() = runTest {
        composeTestRule.setContent {
            SliidepocTheme {
                DisplayHomeScreen(
                    homeViewModel = homeViewModel,
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
        val textSearch = "Home Screen:"
        Thread.sleep(10 * 1000)
        // assert... is triggering the drawing of the composition
        composeTestRule.onNodeWithText(text = "$textSearch 6").assertIsDisplayed()
    }
//    composeTestRule.onRoot().printToLog("TAG")

    @Test
    fun test_openHomeScreenAndCheckText_success() = runTest {
        val textSearch = "Home Screen: TC saved: true"
        composeTestRule.setContent {
            SliidepocTheme {
                DisplayHomeScreen(
                    homeViewModel = homeViewModel,
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
        composeTestRule.onNodeWithText(text = textSearch).assertIsDisplayed()
    }

    @Test
    fun test_openNewsScreenCheckVisibleItems_success() = runTest {
        val countItemsFirstTimeVisibleIncludingHeader = 5
        composeTestRule.setContent {
            SliidepocTheme {
                DisplayNewsScreen(
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
        composeTestRule.onNodeWithTag(TEST_TAG_NEWS_LIST).onChildren()
            .assertCountEquals(countItemsFirstTimeVisibleIncludingHeader)
    }

    @Test
    fun test_openNewsScreenCheckHeader_success() = runTest {
        val titleItemSearch = "\uD83C\uDF3F  Posts"
        composeTestRule.setContent {
            SliidepocTheme {
                DisplayNewsScreen(
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
        composeTestRule.onNodeWithTag(TEST_TAG_NEWS_LIST).onChildren().onFirst()
            .assertTextContains(titleItemSearch)
    }

    @Test
    fun test_openNewsScreenCheckItemIndexFour_success() = runTest {
        val itemIndexNr = 4
        val titleItemSearch = "Post Title 3"
        composeTestRule.setContent {
            SliidepocTheme {
                DisplayNewsScreen(
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
        composeTestRule.onNodeWithTag(TEST_TAG_NEWS_LIST).performScrollToIndex(itemIndexNr)
        composeTestRule.onNodeWithTag(TEST_TAG_NEWS_LIST).onChildren().onFirst().onChildAt(0)
            .assertTextContains(titleItemSearch, substring = true, ignoreCase = true)
    }
}
