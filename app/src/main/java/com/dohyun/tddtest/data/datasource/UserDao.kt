package com.dohyun.tddtest.data.datasource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dohyun.tddtest.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE userId = :userId and password = :password")
    fun getUser(userId: String, password: String): User

    @Query("SELECT * FROM user WHERE userId = :userId and password = :password")
    fun getUser2(userId: String, password: String): LiveData<User>

    @Query("SELECT * FROM user WHERE userId = :userId and password = :password")
    fun getUser3(userId: String, password: String): Flow<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)

    @Delete
    fun delete(user: User)
}