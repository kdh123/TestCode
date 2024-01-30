package com.dohyun.tddtest.data.repository

import android.content.Context
import com.dohyun.tddtest.common.CommonResult
import com.dohyun.tddtest.common.UserFailEvent
import com.dohyun.tddtest.data.datasource.UserLocalDatasource
import com.dohyun.tddtest.data.model.User

class UserRepositoryImpl(context: Context) : UserRepository {

    private val userLocalDatasource: UserLocalDatasource = UserLocalDatasource(context = context)

    override fun getUserData(userId: String, password: String): CommonResult<User, UserFailEvent> {
        return userLocalDatasource.getUserData(userId = userId, password = password)
    }

    override fun insertUserData(user: User) {
        userLocalDatasource.insertUserData(user = user)
    }

    override fun deleteUserData(user: User) {
        userLocalDatasource.deleteUserData(user = user)
    }
}