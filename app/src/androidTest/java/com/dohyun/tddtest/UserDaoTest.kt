package com.dohyun.tddtest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.dohyun.tddtest.data.datasource.UserDao
import com.dohyun.tddtest.data.model.User
import com.dohyun.tddtest.data.room.AppDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class UserDaoTest {

    @get:Rule
    val mainRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java,
        ).build()

        dao = appDatabase.userDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun saveUser() {
        val user = User(userId = "dhkim123", password = "12345678", name = "홍길동", address = "서울시 강남구")
        dao.saveUser(user = user)

        val getUser = dao.getUser("dhkim123", "12345678")

        assertEquals(getUser.name, "홍길동")
    }

    @Test
    fun saveUser2() {
        val user = User(userId = "dhkim123", password = "12345678", name = "홍길동", address = "서울시 강남구")
        dao.saveUser(user = user)

        val getUser = dao.getUser2("dhkim123", "12345678").getOrAwaitValue()

        assertEquals(getUser.name, "홍길동")
    }

    @Test
    fun saveUser3() = runTest {
        val user = User(userId = "dhkim123", password = "12345678", name = "홍길동", address = "서울시 강남구")
        dao.saveUser(user = user)

        val getUser = dao.getUser3("dhkim123", "12345678").first()

        assertEquals(getUser.name, "홍길동")
    }

    @Test
    fun deleteUser() = runTest {
        val user = User(userId = "dhkim123", password = "12345678", name = "홍길동", address = "서울시 강남구")

        dao.saveUser(user = user)
        dao.delete(user = user)

        val getUser = dao.getUser3("dhkim123", "12345678").firstOrNull()

        assertEquals(getUser?.name, null)
    }
}