package com.sliidepoc.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sliidepoc.data.BuildConfig
import com.sliidepoc.data.room.dao.UserDao
import com.sliidepoc.data.room.model.UserImpl

/**
 * Room: Define the room data base operation. Check the room flow for DAO from Android specification
 *
 * @author Ioan Hagau
 * @since 2020.11.24
 */
@Database(
    entities = [UserImpl::class],
    version = BuildConfig.DEFAULT_DATA_BASE_VER,
    exportSchema = false
//    ,
//    autoMigrations = [
//        AutoMigration (from = 1, to = 2)
//    ]
)
abstract class DataBase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {

        // Is important to be a single tone because we need to have only one instance per process.
        // Maintaining more references of the same DB in same process is very expansive.
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        BuildConfig.DEFAULT_DATA_BASE
                    )
                        .enableMultiInstanceInvalidation() // Required for multi-process usage.
                        .fallbackToDestructiveMigration()
                        // Handle some cases during the lifecycle of the DB opened for this app.
                        .addCallback(object : RoomDatabase.Callback() {
//                            override fun onCreate(db: SupportSQLiteDatabase) {
//                                super.onCreate(db)
                            // On a new Thread withContext(Dispatchers.IO) {....do something here CRUD .....}
//                            }
//
//                            override fun onOpen(db: SupportSQLiteDatabase) {
//                                super.onOpen(db)
//                            }
                        })
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
