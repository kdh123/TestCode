package com.dohyun.tddtest

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class CalculatorTest {

    private lateinit var calculator: Calculator

    @Before
    fun setUp() {
        calculator = Calculator()
    }

    @Test
    fun 더하기 () {
        assertEquals(calculator.add(2,3), 8)
    }

    @Test
    fun `빼기` () {
        assertEquals(calculator.minus(2,3), -6)
    }

    @After
    fun tearDown() {

    }
}