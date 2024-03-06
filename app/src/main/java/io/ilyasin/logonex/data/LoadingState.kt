package io.ilyasin.logonex.data

sealed class LoadingState(val error: String? = null) {

    data object Initialized : LoadingState()
    data object InProgress : LoadingState()
    data object Success : LoadingState()
    class Failure(error: String?) : LoadingState(error)
}