package com.jonghyun.bookfinder.viewmodel

import android.os.Handler
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.jonghyun.bookfinder.data.response.BooksApiVolumesResponse
import com.jonghyun.bookfinder.model.GoogleBooksModel
import com.jonghyun.bookfinder.resource.DefaultResource.GOOGLE_BOOKS_API_MAX_RESULT
import com.jonghyun.bookfinder.resource.DefaultResource.TEXT_CHANGED_EVENT_TIME
import com.jonghyun.bookfinder.util.IntUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection


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
    var observableIndicator: ObservableField<String> = ObservableField("")
    private var findSentence: String = ""
    private var viewPager2Fix: Boolean = true

    private val textChangedEventHandler = Handler()
    private val textChangedEventRunnable = Runnable {
        if (findSentence.isEmpty()) {
            clearVolumeList()
        } else {
            googleBooksApi()
        }
    }

    var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (viewPager2Fix) {
                googleBooksApi(position)
            } else {
                viewPager2Fix = true
            }
        }
    }

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        findSentence = s.toString()
        viewPager2Fix = false

        textChangedEventHandler.removeCallbacks(textChangedEventRunnable)
        textChangedEventHandler.postDelayed(textChangedEventRunnable, TEXT_CHANGED_EVENT_TIME)
    }

    fun googleBooksApi(startIndex: Int = 0) {
        _isProgress.postValue(true)
        googleBooksModel.getBooks(findSentence, startIndex * GOOGLE_BOOKS_API_MAX_RESULT)
            .enqueue(object : Callback<BooksApiVolumesResponse> {
                override fun onFailure(call: Call<BooksApiVolumesResponse>, t: Throwable) {
                    _toastMessage.postValue(t.message)
                    _isProgress.postValue(false)
                }

                override fun onResponse(
                    call: Call<BooksApiVolumesResponse>,
                    response: Response<BooksApiVolumesResponse>
                ) {
                    var responseTotalBookCount = 0
                    when (response.code()) {
                        HttpURLConnection.HTTP_OK -> {
                            response.body()?.let { booksApiVolumesResponse ->
                                responseTotalBookCount = booksApiVolumesResponse.totalItems // DYNAMIC UI
                                _volumeList.postValue(booksApiVolumesResponse.items ?: emptyList())
                            }
                        }
                        HttpURLConnection.HTTP_BAD_REQUEST -> {
                            clearVolumeList()
                        }
                        else -> {
                            _toastMessage.postValue(response.code().toString())
                            clearVolumeList()
                        }
                    }
                    observableTotalCount.set(responseTotalBookCount)
                    _totalBooksCount.postValue(responseTotalBookCount)
                    observableIndicator.set(getIndicator(startIndex, responseTotalBookCount))
                    _isProgress.postValue(false)
                }
            })
    }

    private fun getIndicator(position: Int, totalBooksCount: Int): String {
        val startIndicator = (position * GOOGLE_BOOKS_API_MAX_RESULT) + 1
        var endIndicator = IntUtil.getSmallerInt((position + 1) * GOOGLE_BOOKS_API_MAX_RESULT, totalBooksCount)
        return "[$startIndicator ~ $endIndicator]"
    }

    private fun clearVolumeList() {
        _volumeList.postValue(emptyList())
        observableTotalCount.set(0)
    }
}