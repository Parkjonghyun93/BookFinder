package com.jonghyun.bookfinder.viewmodel

import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.jonghyun.bookfinder.data.response.BooksApiVolumesResponse
import com.jonghyun.bookfinder.model.GoogleBooksModel
import com.jonghyun.bookfinder.resource.DefaultResource.GOOGLE_BOOKS_API_MAX_RESULT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FindViewModel(private val googleBooksModel: GoogleBooksModel) : BaseViewModel() {
    private var _volumeList = MutableLiveData<List<BooksApiVolumesResponse.Volume>>()
    val volumeList: LiveData<List<BooksApiVolumesResponse.Volume>>
        get() = _volumeList
    private var _totalBooksCount = MutableLiveData<Int>()
    val totalBooksCount: LiveData<Int>
        get() = _totalBooksCount
    private var _toastMessage = MutableLiveData<String>()
    val toastErrorMessage: LiveData<String>
        get() = _toastMessage

    var observableTotalCount: ObservableInt = ObservableInt(0)
    private var findSentence: String = ""

    var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            _isProgress.postValue(true)
            googleBooksApi(position)
        }
    }

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        findSentence = s.toString()
        googleBooksApi()
    }

    fun googleBooksApi(startIndex: Int = 0) {
        googleBooksModel.getBooks(findSentence, startIndex * GOOGLE_BOOKS_API_MAX_RESULT)
            .enqueue(object : Callback<BooksApiVolumesResponse> {
                override fun onFailure(call: Call<BooksApiVolumesResponse>, t: Throwable) {
                    _toastMessage.postValue(t.message)
                    _isProgress.postValue(false)
                }

                override fun onResponse(call: Call<BooksApiVolumesResponse>, response: Response<BooksApiVolumesResponse>) {
                    var responseTotalBookCount = 0
                    when (response.code()) {
                        200 -> {
                            if (findSentence.isNotEmpty()) {
                                response.body()?.let { booksApiVolumesResponse ->
                                    responseTotalBookCount = booksApiVolumesResponse.totalItems // DYNAMIC UI
                                    _volumeList.postValue(booksApiVolumesResponse.items ?: emptyList())
                                }
                            }
                        }
                        400 -> { clearVolumeList() }
                        else -> {
                            _toastMessage.postValue(response.code().toString())
                            clearVolumeList()
                        }
                    }
                    observableTotalCount.set(responseTotalBookCount)
                    _totalBooksCount.postValue(responseTotalBookCount)
                    _isProgress.postValue(false)
                }
            })
    }

    private fun clearVolumeList() {
        _volumeList.postValue(emptyList())
    }
}