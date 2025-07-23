package com.example.omnitek.core


sealed class DataState<T> (
    var message: String? = null,
    var data: T? = null,
){
    class Loading<T>: DataState<T>()
    class Success<T>(mdata: T?): DataState<T>(data = mdata)
    class Error<T>(message: String?): DataState<T>(message = message)

}