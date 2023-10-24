package com.internshalaworkshop.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.internshalaworkshop.models.UsersEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UsersEntity)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun getUser(email: String, password: String): UsersEntity?


}
