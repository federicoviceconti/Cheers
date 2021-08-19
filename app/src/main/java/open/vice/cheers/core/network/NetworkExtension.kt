package open.vice.cheers.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import open.vice.cheers.network.exception.ApiException
import open.vice.cheers.network.model.ErrorResult
import open.vice.cheers.network.model.ResponseData
import open.vice.cheers.network.model.SuccessResult
import retrofit2.HttpException

fun <T> getDefaultError(): ErrorResult<T> =
    ErrorResult(ApiException.GenericErrorException())

suspend fun <T> makeRequest(
    request: suspend () -> T
): ResponseData<T> {
    return try {
        val response = request.invoke()
        SuccessResult(response)
    } catch (e: HttpException) {
        getDefaultError()
    } catch (e: Exception) {
        getDefaultError()
    }
}
