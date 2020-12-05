package com.example.uiexample

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast

class ToastUtils private constructor(private val context: Context) {

    private val toast: Toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)

    companion object {
        @Volatile
        private lateinit var instance: ToastUtils

        public fun get(context: Context): ToastUtils {
            if (!::instance.isInitialized) {
                synchronized(this::class.java) {
                    if (!::instance.isInitialized) {
                        instance = ToastUtils(context)
                    }
                }
            }
            return instance
        }
    }

    public fun setView(view: View): ToastUtils {
        toast.view = view
        return this
    }

    public fun setCustomViewText(viewId: Int, text: CharSequence): ToastUtils {
        val customView = toast.view
        if (customView != null) {
            val tv = customView.findViewById<TextView>(viewId)
            tv.text = text
        }
        return this
    }

    public fun setText(text: CharSequence): ToastUtils {
        toast.setText(text)
        return this
    }

    public fun setDuration(duration: Int): ToastUtils {
        toast.duration = duration
        return this
    }

    public fun show() {
        if (toast.duration == 0) {
            toast.duration = Toast.LENGTH_SHORT
        }
        toast.show()
    }
}