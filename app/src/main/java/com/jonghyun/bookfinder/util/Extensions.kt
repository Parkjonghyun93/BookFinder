package com.jonghyun.bookfinder.util


fun Int.compareForSmaller(a: Int): Int  {
    if (this < a) {
        return this
    }
    return a
}
