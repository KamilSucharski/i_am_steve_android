package com.iamsteve.data.interceptor

import com.iamsteve.domain.exception.NetworkException
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        response
            .takeUnless { it.code in 200..299 }
            ?.let { throw NetworkException(it.code.toString()) }

        return response
    }
}