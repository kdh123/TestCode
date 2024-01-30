package com.dohyun.tddtest.common

sealed class CommonResult<T, F>(val data: T? = null, val failEvent: F? = null) {
    class Success<T, F>(data: T) : CommonResult<T, F>(data = data)
    class Fail<T, F>(failEvent: F) : CommonResult<T, F>(failEvent = failEvent)
}