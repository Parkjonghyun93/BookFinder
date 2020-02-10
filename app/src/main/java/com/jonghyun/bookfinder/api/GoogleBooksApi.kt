package com.jonghyun.bookfinder.api

import com.jonghyun.bookfinder.data.response.BooksApiVolumesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/*
 * startIndex default : 0
 * maxResult default : 10 (max:40)
 * */
interface GoogleBooksApi {
    @GET("volumes?")
    fun findBooks(
        @Query("q") q: String,
        @Query("startIndex") startIndex: Int,
        @Query("maxResults") maxResults: Int
    ): Call<BooksApiVolumesResponse>
}

