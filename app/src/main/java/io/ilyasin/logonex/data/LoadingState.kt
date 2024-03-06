package io.ilyasin.logonex.data

/**
 * A sealed class to represent the loading state of a data request
 */
sealed class LoadingState(val error: String? = null) {

    data object Initialized : LoadingState()
    data object InProgress : LoadingState()
    data object Success : LoadingState()
    class Failure(error: String?) : LoadingState(error)
}