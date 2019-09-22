package com.example.core.network.model

data class NetworkResponse<T>(val isSuccess: Boolean, val data: T, val error: String)
