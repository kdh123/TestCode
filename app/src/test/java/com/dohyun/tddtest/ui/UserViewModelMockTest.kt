package com.dohyun.tddtest.ui

import com.dohyun.tddtest.common.CommonResult
import com.dohyun.tddtest.common.UserFailEvent
import com.dohyun.tddtest.data.model.User
import com.dohyun.tddtest.data.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class UserViewModelMockTest {

    lateinit var userViewModel: UserViewModel

    @Mock
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userViewModel = UserViewModel(userRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `ID가 빈 값일 때`() {
        `when`(userRepository.getUserData("", password = "nudgeHealthcare")).thenReturn(CommonResult.Fail(failEvent = UserFailEvent.NoUserId))

        userViewModel.getUser(userId = "", password = "nudgeHealthcare")

        verify(userRepository, never()).getUserData("", password = "nudgeHealthcare")

        assertEquals(userViewModel.state.value, UserState.Fail(failEvent = UserFailEvent.NoUserId))
    }

    @Test
    fun `passwrod가 8자 미만일 경우`() {
        `when`(userRepository.getUserData("cashwalk", password = "1234")).thenReturn(CommonResult.Fail(failEvent = UserFailEvent.ShortPassword))

        userViewModel.getUser(userId = "cashwalk", password = "1234")

        verify(userRepository, never()).getUserData("cashwalk", password = "1234")

        assertEquals(userViewModel.state.value, UserState.Fail(failEvent = UserFailEvent.ShortPassword))
    }

    @Test
    fun `유저를 찾았을 때`() {
        val user = User(userId = "cashwalk", password = "12345678910", name = "홍길동", address = "서울시 강남구")

        `when`(userRepository.getUserData(userId = "cashwalk", password = "12345678910")).thenReturn(
            CommonResult.Success(
                data = User(
                    "cashwalk",
                    "12345678910",
                    "홍길동",
                    "서울시 강남구"
                )
            )
        )

        userViewModel.getUser(userId = "cashwalk", password = "12345678910")

        verify(userRepository).getUserData(userId = "cashwalk", password = "12345678910")

        assertEquals(userViewModel.state.value, UserState.Success(user = user))
    }
}