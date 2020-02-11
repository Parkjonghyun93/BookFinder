package com.jonghyun.bookfinder.di

import com.jonghyun.bookfinder.api.GoogleBooksApi
import com.jonghyun.bookfinder.resource.BaseUrl
import com.jonghyun.bookfinder.model.GoogleBooksModel
import com.jonghyun.bookfinder.model.GoogleBooksModelImpl
import com.jonghyun.bookfinder.view.CustomProgressDialog
import com.jonghyun.bookfinder.view.adapter.BookListAdapter
import com.jonghyun.bookfinder.view.adapter.ScreenSlidePagerAdapter
import com.jonghyun.bookfinder.viewmodel.FindViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


var bookFindRetrofitModule = module {
    single<GoogleBooksApi> {
        Retrofit.Builder()
            .baseUrl(BaseUrl.googleBooksApiBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleBooksApi::class.java)
    }
}

var customProgressModule = module {
    single { CustomProgressDialog(it[0]) }
}

var googleBooksModelModule = module {
    factory<GoogleBooksModel> {
        GoogleBooksModelImpl(get())
    }
}

var findViewModelModule = module {
    viewModel {
        FindViewModel(get())
    }
}

var bookListAdapterModule = module {
    factory {
        BookListAdapter()
    }
}

var slidePageAdapterModule = module {
    factory {
        ScreenSlidePagerAdapter(it[0])
    }
}

var koinModules =
    listOf(bookFindRetrofitModule, googleBooksModelModule, findViewModelModule, bookListAdapterModule, slidePageAdapterModule, customProgressModule)