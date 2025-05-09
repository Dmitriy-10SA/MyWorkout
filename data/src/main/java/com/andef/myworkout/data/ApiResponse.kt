package com.andef.myworkout.data

import android.util.Log
import com.andef.myworkout.data.ApiException.BadRequest
import com.andef.myworkout.data.ApiException.Forbidden
import com.andef.myworkout.data.ApiException.NotFound
import com.andef.myworkout.data.ApiException.NullableBodyError
import com.andef.myworkout.data.ApiException.RequestTimeout
import com.andef.myworkout.data.ApiException.ServerError
import com.andef.myworkout.data.ApiException.Unauthorized
import retrofit2.Response
import javax.inject.Inject

/**
 * @property parseResponseWithNullableBody получение ответа или ошибки, учитывая, что тело != null
 * @property parseResponseWithNullableBody получение ответа или ошибки, учитывая, что тело м.б null
 * @property parseException получение ошибки по статус коду
 * @property logAndThrowException логирование полученой ошибки и ее выбрасывание
 *
 * @see ApiException
 */
class ApiResponse @Inject constructor() {
    fun <T> parseResponseWithNullableBody(response: Response<T>): T? {
        return if (response.isSuccessful) {
            response.body()
        } else {
            logAndThrowException(parseException(response))
        }
    }

    fun <T> parseResponseWithoutNullableBody(response: Response<T>): T {
        return if (response.isSuccessful) {
            response.body() ?: logAndThrowException(NullableBodyError)
        } else {
            logAndThrowException(parseException(response))
        }
    }

    private fun <T> parseException(response: Response<T>): ApiException {
        Log.d("okhttp.OkHttpClient", "----------------------------------------")
        Log.d("okhttp.OkHttpClient", "$response")
        return when (response.code()) {
            400 -> BadRequest
            401 -> Unauthorized
            403 -> Forbidden
            404 -> NotFound
            408 -> RequestTimeout
            500, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511, 599 -> ServerError
            else -> ApiException.UnknownError
        }
    }

    private fun logAndThrowException(exception: ApiException): Nothing {
        Log.d("okhttp.OkHttpClient", "${exception.message}")
        Log.d("okhttp.OkHttpClient", "----------------------------------------")
        throw exception
    }
}