package com.sliidepoc.ui.mock.data.http

import com.sliidepoc.data.http.api.UsersProxy
import com.sliidepoc.domain.api.data.http.models.Post
import com.sliidepoc.domain.api.data.http.models.PostDetail
import com.sliidepoc.ui.mock.data.http.models.MockPostDetailImpl
import com.sliidepoc.ui.mock.data.http.models.MockPostImpl

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
class MockUsersProxy : UsersProxy {
    override suspend fun getPosts(): List<Post> {
        return listOf(
            MockPostImpl("1", "user1", "Post Title 1", "Post Description 1"),
            MockPostImpl("2", "user2", "Post Title 2", "Post Description 2"),
            MockPostImpl("3", "user3", "Post Title 3", "Post Description 3"),
            MockPostImpl("4", "user4", "Post Title 4", "Post Description 4"),
            MockPostImpl("5", "user5", "Post Title 5", "Post Description 5"),
            MockPostImpl("6", "user6", "Post Title 6", "Post Description 6")
        )
    }

    override suspend fun getPostDetail(id: Int): PostDetail {
        return MockPostDetailImpl(
            "1",
            "The very first thing the android system is doing when you tap on the icon",
            "At this point our application is not even created yet so we don’t have control to measure it. However since everything is done in main thread, on the first line of code that will be executed by the system we will get the time since the main thread is alive."
        )
    }
}
