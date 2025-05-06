package com.andef.myworkout.data

/**
 * @property BadRequest в запросе клиента обнаружена синтаксическая ошибка
 * @property Unauthorized неавторизованный запрос клиента
 * @property Forbidden доступ к ресурсу запрещен клиенту
 * @property NotFound разыскиваемый клиентом ресурс не найден
 * @property RequestTimeout превышено время ожидания на запрос клиента
 * @property ServerError ошибки на стороне сервера
 * @property NullableBodyError ошибка, когда нам приходит null там, где он приходить не должен
 */
sealed class ApiException(msg: String) : Exception(msg) {
    data object BadRequest : ApiException("400 BadRequest") {
        private fun readResolve(): Any = BadRequest
    }

    data object Unauthorized : ApiException("401 Unauthorized") {
        private fun readResolve(): Any = Unauthorized
    }

    data object Forbidden : ApiException("403 Forbidden") {
        private fun readResolve(): Any = Forbidden
    }

    data object NotFound : ApiException("404 NotFound") {
        private fun readResolve(): Any = NotFound
    }

    data object RequestTimeout : ApiException("408 RequestTimeout") {
        private fun readResolve(): Any = RequestTimeout
    }

    data object ServerError : ApiException("5XX ServerError") {
        private fun readResolve(): Any = ServerError
    }

    data object NullableBodyError : ApiException("Nullable Body Error") {
        private fun readResolve(): Any = NullableBodyError
    }
}