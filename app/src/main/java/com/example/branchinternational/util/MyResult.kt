package com.example.branchinternational.util

sealed class MyResult<out T> {
    data class Success<out T>(val data: T) : MyResult<T>()
    data class Error(val exception: Exception) : MyResult<Nothing>()

    fun isSuccess(): Boolean = this is Success<*>
    fun isError(): Boolean = this is Error
}
