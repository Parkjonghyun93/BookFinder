package com.jonghyun.bookfinder.model

import com.jonghyun.bookfinder.data.response.BooksApiVolumesResponse
import com.jonghyun.bookfinder.resource.DefaultResource.GOOGLE_BOOKS_API_MAX_RESULT
import retrofit2.Call


interface GoogleBooksModel {
    fun getBooks(insertWord: String, startIndex: Int = 0, maxResult: Int = GOOGLE_BOOKS_API_MAX_RESULT):
            Call<BooksApiVolumesResponse>
}