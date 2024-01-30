package com.dohyun.tddtest.data.datasource

import android.content.Context
import com.dohyun.tddtest.common.CommonResult
import com.dohyun.tddtest.common.UserFailEvent
import com.dohyun.tddtest.data.model.User
import com.dohyun.tddtest.data.room.AppDatabase
import java.lang.Exception

class UserLocalDatasource(context: Context) {
    private val db = AppDatabase.getInstance(context = context)
    private val service = db.userDao()

    fun getUserData(userId: String, password: String) : CommonResult<User, UserFailEvent> {
        return try {
            val user = service.getUser(userId = userId, password = password)
            CommonResult.Success(data = user)
        } catch (e: Exception) {
            CommonResult.Fail(failEvent = UserFailEvent.UnexpectedError)
        }
    }

    fun insertUserData(user: User) {
        service.saveUser(user = user)
    }

    fun deleteUserData(user: User) {
        service.delete(user = user)
    }
}