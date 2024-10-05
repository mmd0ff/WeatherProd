package com.example.weatherprod

import com.example.weatherprod.Model.ErrorModel

sealed class ApiResult<out T> {
    data class Success<T>(val data: T?): ApiResult<T>()
    data class Error(val error: ErrorModel?): ApiResult<Nothing>()
}