package com.jonghyun.bookfinder.util

object IntUtil {
    fun getBiggerInt(a: Int, b: Int): Int {
        if (a > b) {
            return a
        }
        return b
    }
    fun getSmallerInt(a: Int, b: Int): Int {
        if (a < b) {
            return a
        }
        return b
    }
}