package com.dohyun.tddtest.ui

import com.dohyun.tddtest.common.UserFailEvent
import com.dohyun.tddtest.data.model.User
import com.dohyun.tddtest.data.repository.FakeUserRepositoryImpl
import com.dohyun.tddtest.data.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserViewModelFakeTest {

    lateinit var userViewModel: UserViewModel
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userRepository = FakeUserRepositoryImpl()
        userViewModel = UserViewModel(userRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `ID가 빈 값일 때`() {
        userViewModel.getUser(userId = "", password = "nudgeHealthcare")

        assertEquals(userViewModel.state.value, UserState.Fail(failEvent = UserFailEvent.NoUserId))
    }

    @Test
    fun `passwrod가 8자 미만일 경우`() {
        userViewModel.getUser(userId = "cashwalk", password = "1234")

        assertEquals(userViewModel.state.value, UserState.Fail(failEvent = UserFailEvent.ShortPassword))
    }

    @Test
    fun `유저를 찾았을 때`() {
        val user = User(userId = "cashwalk", password = "12345678910", name = "홍길동", address = "서울시 강남구")

        userViewModel.saveUser(user = user)
        userViewModel.getUser(userId = "cashwalk", password = "12345678910")

        assertEquals(userViewModel.state.value, UserState.Success(user = user))
    }

    @Test
    fun `일치하는 유저가 존재하지 않을 때`() {
        val user = User(userId = "cashwalk", password = "12345678910", name = "홍길동", address = "서울시 강남구")

        userViewModel.saveUser(user = user)
        userViewModel.getUser(userId = "cashwalk2", password = "12345678910")

        assertEquals(userViewModel.state.value, UserState.Fail(failEvent = UserFailEvent.NoUser))
    }
}