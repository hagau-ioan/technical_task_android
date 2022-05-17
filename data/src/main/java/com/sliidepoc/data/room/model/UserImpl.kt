package com.sliidepoc.data.room.model

/**
 * Room: Define the room operation with USER table. Check the room flow for DAO from Android specification
 * This is the concrete implementation of User entity.
 *
 * @author Ioan Hagau
 * @since 2020.11.24
 */
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserImpl(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val extId: Int,

    val name: String,

    val email: String?,

    val gender: String?,

    val status: String?,

    val creationDateTime: String
)
