package com.dohyun.tddtest.data.repository

import com.dohyun.tddtest.common.CommonResult
import com.dohyun.tddtest.common.UserFailEvent
import com.dohyun.tddtest.data.model.User

class FakeUserRepositoryImpl: UserRepository {

    private val userList = mutableListOf<User>()

    override fun getUserData(userId: String, password: String) : CommonResult<User, UserFailEvent> {
        val user = userList.filter { it.userId == userId && it.password == password }.run {
            if (isNotEmpty()) {
                get(0)
            } else {
                null
            }
        }

        return user?.let {
            CommonResult.Success(data = it)
        } ?: kotlin.run {
            CommonResult.Fail(failEvent = UserFailEvent.NoUser)
        }
    }

    override fun insertUserData(user: User) {
        userList.add(user)
    }

    override fun deleteUserData(user: User) {
        userList.remove(user)
    }
}