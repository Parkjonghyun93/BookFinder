package com.jonghyun.bookfinder.view

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.widget.AppCompatImageView
import com.jonghyun.bookfinder.R


class CustomProgressDialog(context: Context) {
    private var dialog: AppCompatDialog? = null
    init {
        dialog = AppCompatDialog(context)
        initView()
    }

    fun execute(isProgress: Boolean) {
        if (isProgress) {
            show()
        } else {
            dismiss()
        }
    }

    private fun initView() {
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.progress_layout)
        val progress = dialog?.findViewById<AppCompatImageView>(R.id.iv_progress)
        val animation: AnimationDrawable = progress?.background as AnimationDrawable
        progress.post { animation.start() }
    }


    private fun show() {
        dialog?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    private fun dismiss() {
        dialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}