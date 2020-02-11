package com.jonghyun.bookfinder.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jonghyun.bookfinder.R
import com.jonghyun.bookfinder.resource.DefaultResource.GOOGLE_BOOKS_API_MAX_RESULT
import com.jonghyun.bookfinder.view.adapter.BookListAdapter
import com.jonghyun.bookfinder.viewmodel.FindViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

class BookListFragment : Fragment(), BookListAdapter.ItemClickListener {
    private val bookListAdapter: BookListAdapter by inject()
    private val findViewModel: FindViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = View.inflate(context, R.layout.fragment_book_list, container)
        initView(view)
        initObserve()

        return view
    }

    private fun initView(view: View) {
        val rcv = view.findViewById<RecyclerView>(R.id.rcv_book_list_list)

        rcv.layoutManager = LinearLayoutManager(context)
        bookListAdapter.bookListItemClickListener = this
        rcv.adapter = bookListAdapter
    }

    private fun initObserve() {
        findViewModel.volumeList.observe(this, Observer {
            bookListAdapter.bookList = it
            bookListAdapter.notifyDataSetChanged()
        })
    }

    override fun clickItem(id: String) {
        val webIntent: Intent = Uri.parse(getString(R.string.google_books_detail_api, id)).let { webPage ->
            Intent(Intent.ACTION_VIEW, webPage)
        }
        startActivity(webIntent)
    }


    companion object {
        private const val ARG_START_INDEX = "ARG_START_INDEX"

        fun newInstance(pageCount: Int): BookListFragment {
            val fragment = BookListFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_START_INDEX, pageCount * GOOGLE_BOOKS_API_MAX_RESULT)
            fragment.arguments = bundle
            return fragment
        }
    }
}

