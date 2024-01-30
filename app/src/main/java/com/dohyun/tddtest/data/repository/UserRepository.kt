package com.dohyun.tddtest.data.repository

import com.dohyun.tddtest.common.CommonResult
import com.dohyun.tddtest.common.UserFailEvent
import com.dohyun.tddtest.data.model.User

interface UserRepository {
    fun getUserData(userId: String, password: String): CommonResult<User, UserFailEvent>
    fun insertUserData(user: User)
    fun deleteUserData(user: User)
}