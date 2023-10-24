package com.internshalaworkshop.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.internshalaworkshop.models.AppliedWorkshopEntity
import com.internshalaworkshop.models.UsersEntity
import com.internshalaworkshop.models.WorkshopEntity
@Database(entities = [UsersEntity::class, AppliedWorkshopEntity::class, WorkshopEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun appliedWorkshopDao(): AppliedWorkshopDao
    abstract fun workshopDao(): WorkshopDao

    companion object {
        private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "my-app-db"
                    ).build()
                }
                return instance!!
            }
        }
    }
}
