package com.dohyun.tddtest.ui

import com.dohyun.tddtest.common.UserFailEvent
import com.dohyun.tddtest.data.model.User

sealed interface UserState {

    object None: UserState
    data class Success(val user: User): UserState
    data class Fail(val failEvent: UserFailEvent): UserState
}