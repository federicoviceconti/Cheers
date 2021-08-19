package open.vice.cheers.network.model

import open.vice.cheers.network.exception.ApiException

sealed class ResponseData<T> {
    fun hasError() = (this is ErrorResult) || (this is SuccessResult && this.getResponseData() == null)

    fun getError(): ApiException = (this as ErrorResult).exception

    fun getResponseData(): T = (this as SuccessResult).data
}

class SuccessResult<T>(val data: T) : ResponseData<T>()

class ErrorResult<Any>(val exception: ApiException) : ResponseData<Any>()