package com.sliidepoc.ui

import com.sliidepoc.data.room.dao.UserDao
import com.sliidepoc.data.room.database.DataBase
import com.sliidepoc.data.room.model.UserImpl
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
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
class DataBaseTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var dataBase: DataBase

    private val dispatcher = StandardTestDispatcher()

    private lateinit var userDao: UserDao

    @Before
    fun before() {
        Dispatchers.setMain(dispatcher)
        hiltRule.inject()
        userDao = dataBase.userDao
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun test_insertUserCheckIfIsAddedToDB_success() = runTest {
        userDao.insert(
            UserImpl(
                user_email = "hagau.ioan@gmail.com",
                user_nickname = "my nickname",
                user_password = "password here",
                user_userId = 123456L,
                user_userName = "Hagau Ioan"
            )
        )
        Assert.assertEquals(true, userDao.countUsers() > 0)
    }
}
