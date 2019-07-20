package com.app.util

sealed class NetworkState<T>(val message: String? = null) {
    class Success<T> : NetworkState<T>()
    class Loading<T> : NetworkState<T>()
    class Error<T>(message: String) : NetworkState<T>(message)
}
