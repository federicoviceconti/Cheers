package open.vice.cheers.network.exception

sealed class ApiException: Exception() {
    class GenericErrorException: ApiException()

    class NoConnectedToInternetException: ApiException()

    class MalformedResponseException: ApiException()
}