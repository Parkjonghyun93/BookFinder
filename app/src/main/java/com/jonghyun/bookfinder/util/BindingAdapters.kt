package com.jonghyun.bookfinder.util

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2


// viewPage2 이벤트 수신 dataBinding attribute
@BindingAdapter("app:registerOnPageChangeCallback")
fun registerOnPageChangeCallback(viewPager2: ViewPager2, registerOnPageChangeCallback: ViewPager2.OnPageChangeCallback) {
    viewPager2.registerOnPageChangeCallback(registerOnPageChangeCallback)
}

