package com.jonghyun.bookfinder.util

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2


@BindingAdapter("app:registerOnPageChangeCallback")
fun registerOnPageChangeCallback(viewPager2: ViewPager2, registerOnPageChangeCallback: ViewPager2.OnPageChangeCallback) {
    viewPager2.registerOnPageChangeCallback(registerOnPageChangeCallback)
}

