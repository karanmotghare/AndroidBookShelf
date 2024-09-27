package com.example.myapplication

sealed class LibraryResultEvent<out R> {
    data class OnSuccess<out T>(val data:T):LibraryResultEvent<T>()
    data class OnFailure(val msg:String, val data: Any? = null):LibraryResultEvent<Nothing>()
    object OnLoading:LibraryResultEvent<Nothing>()
}

val <T>LibraryResultEvent<T>.value:T?
    get() = (this as? LibraryResultEvent.OnSuccess)?.data