package com.jonghyun.bookfinder.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jonghyun.bookfinder.R
import com.jonghyun.bookfinder.databinding.ActivityFindBinding
import com.jonghyun.bookfinder.resource.DefaultResource.GOOGLE_BOOKS_API_MAX_RESULT
import com.jonghyun.bookfinder.view.adapter.ScreenSlidePagerAdapter
import com.jonghyun.bookfinder.viewmodel.FindViewModel
import kotlinx.android.synthetic.main.activity_find.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class FindActivity : AppCompatActivity() {
    private lateinit var viewDataBinding: ActivityFindBinding
    private val findViewModel: FindViewModel by viewModel()
    private val screenSlidePagerAdapter: ScreenSlidePagerAdapter by inject { parametersOf(this) }
    private val customProgressDialog: CustomProgressDialog by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initObserver()
    }

    private fun initView() {
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_find)
        viewDataBinding.viewmodel = findViewModel
        vp2_find_activity.adapter = screenSlidePagerAdapter
    }

    private fun initObserver() {
        findViewModel.isProgress.observe(this, Observer {
            customProgressDialog.execute(it)
        })

        findViewModel.totalBooksCount.observe(this, Observer {
            screenSlidePagerAdapter.pageCount = it / GOOGLE_BOOKS_API_MAX_RESULT + 1
            screenSlidePagerAdapter.notifyDataSetChanged()
        })

        findViewModel.toastErrorMessage.observe(this, Observer {
            Toast.makeText(
                applicationContext,
                applicationContext.getString(R.string.snack_msg_fail, it),
                Toast.LENGTH_SHORT
            ).show()
        })
    }
}
