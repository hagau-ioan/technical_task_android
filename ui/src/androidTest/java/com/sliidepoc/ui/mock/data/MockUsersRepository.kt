package com.sliidepoc.ui.mock.data

import com.sliidepoc.data.http.api.UsersProxy
import com.sliidepoc.domain.api.data.UsersRepository
import com.sliidepoc.domain.api.data.http.models.Post
import com.sliidepoc.domain.api.data.http.models.PostDetail

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
class MockUsersRepository constructor(
    private val usersProxy: UsersProxy
) : UsersRepository {

    override suspend fun getPosts(): List<Post> {
        return usersProxy.getPosts()
    }

    override suspend fun getPostDetail(id: Int): PostDetail? {
        return usersProxy.getPostDetail(id)
    }
}
