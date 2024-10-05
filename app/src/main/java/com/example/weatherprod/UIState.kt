package com.example.weatherprod

sealed class UIState<out T> {
    data class Success<T>(val data: T): UIState<T>()
    data class Loading(val isLoading: Boolean): UIState<Nothing>()
    data class Error(val errorCode: Int?, val errorMessage: String?): UIState<Nothing>()
}