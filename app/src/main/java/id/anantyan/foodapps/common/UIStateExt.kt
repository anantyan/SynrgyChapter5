package id.anantyan.foodapps.common

sealed class UIState<T>(
    val data: T? = null,
    val message: Int? = null,
    val code: Int? = null
) {
    class Success<T>(data: T) : UIState<T>(data = data)
    class Error<T>(code: Int?, message: Int) : UIState<T>(code = code, message = message)
    class Loading<T> : UIState<T>()
}