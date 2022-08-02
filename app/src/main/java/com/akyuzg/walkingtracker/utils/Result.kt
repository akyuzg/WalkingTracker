package com.akyuzg.walkingtracker.utils

/**
 * A generic class for combining data with its status
 * @param <T>
 */
sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: String) : Result<Nothing>()
}