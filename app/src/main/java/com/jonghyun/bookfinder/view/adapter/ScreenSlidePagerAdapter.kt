package com.jonghyun.bookfinder.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jonghyun.bookfinder.view.BookListFragment

class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    var pageCount: Int = 0
    override fun getItemCount(): Int = pageCount
    override fun createFragment(pageCount: Int): Fragment = BookListFragment.newInstance(pageCount)
}