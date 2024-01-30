package com.dohyun.tddtest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dohyun.tddtest.common.UserFailEvent
import com.dohyun.tddtest.common.CommonResult
import com.dohyun.tddtest.data.model.User
import com.dohyun.tddtest.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.IllegalArgumentException

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _state: MutableStateFlow<UserState> = MutableStateFlow(UserState.None)
    val state = _state.asStateFlow()

    fun getUser(userId: String, password: String) {
        if (password.length < 8) {
            _state.value = UserState.Fail(failEvent = UserFailEvent.ShortPassword)
            return
        }

        if (userId.isEmpty()) {
            _state.value = UserState.Fail(failEvent = UserFailEvent.NoUserId)
            return
        }

        val result = userRepository.getUserData(userId = userId, password = password)

        when (result) {
            is CommonResult.Success -> {
                _state.value = UserState.Success(result.data!!)
            }

            is CommonResult.Fail -> {
                _state.value = UserState.Fail(failEvent = result.failEvent!!)
            }
        }
    }

    fun saveUser(user: User) {
        userRepository.insertUserData(user = user)
    }

    fun deleteUser(user: User) {
        userRepository.deleteUserData(user = user)
    }
}

class ViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}