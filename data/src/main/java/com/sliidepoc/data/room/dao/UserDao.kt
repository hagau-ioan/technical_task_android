package com.sliidepoc.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sliidepoc.data.room.model.UserImpl

/**
 * Room: Define the room operation with USER table. Check the room flow for DAO from Android specification
 *
 * @author Ioan Hagau
 * @since 2020.11.24
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserImpl): Long

    @Query("SELECT * from users")
    suspend fun getUsers(): List<UserImpl>

    @Query("DELETE FROM users")
    fun clearAll()

    @Query("DELETE FROM users WHERE extId = :id")
    fun delete(id: Int)

    @Query("SELECT count(*) FROM users")
    fun countUsers(): Int
}
