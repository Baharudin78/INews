package com.baharudin.inews.utils

sealed class Result<T>(val data : T? = null, val message : String? = null) {
    class Sucess<T>(data: T?, message: String? = null) : Result<T>(data, message)
    class Error<T>(message: String?, data: T? = null) : Result<T>(data, message)
    class Loading<T>() : Result<T>()
}
