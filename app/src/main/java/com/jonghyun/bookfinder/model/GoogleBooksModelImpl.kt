package com.jonghyun.bookfinder.model

import com.jonghyun.bookfinder.api.GoogleBooksApi
import com.jonghyun.bookfinder.data.response.BooksApiVolumesResponse
import retrofit2.Call


class GoogleBooksModelImpl(private val api: GoogleBooksApi): GoogleBooksModel {
    override fun getBooks(insertWord: String, startIndex: Int, maxResult: Int): Call<BooksApiVolumesResponse> {
        return api.findBooks(insertWord, startIndex, maxResult)
    }
}