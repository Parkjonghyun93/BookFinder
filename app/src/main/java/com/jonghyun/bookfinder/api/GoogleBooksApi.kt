package com.jonghyun.bookfinder.api

import com.jonghyun.bookfinder.data.response.BooksApiVolumesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * q description
    intitle: Returns results where the text following this keyword is found in the title.
    inauthor: Returns results where the text following this keyword is found in the author.
    inpublisher: Returns results where the text following this keyword is found in the publisher.
    subject: Returns results where the text following this keyword is listed in the category list of the volume.
    isbn: Returns results where the text following this keyword is the ISBN number.
    lccn: Returns results where the text following this keyword is the Library of Congress Control Number.
    oclc: Returns results where the text following this keyword is the Online Computer Library Center number
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

