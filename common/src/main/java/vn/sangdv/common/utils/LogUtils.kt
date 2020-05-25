package vn.sangdv.common.utils

import android.util.Log
import vn.sangdv.common.BuildConfig

object LogUtils{
    fun i(tag: String?, message: String?) {
        if (BuildConfig.DEBUG) {
            if (tag != null && message != null)
                Log.i(tag, message)
        }
    }

    fun d(tag: String?, message: String?) {
        if (BuildConfig.DEBUG) {
            if (tag != null && message != null)
                Log.d(tag, message)
        }
    }

    fun e(tag: String?, message: String?) {
        if (BuildConfig.DEBUG) {
            if (tag != null && message != null)
                Log.e(tag, message)
        }
    }

    fun d(message: String?) {
        if (BuildConfig.DEBUG) {
            if (message != null)
                Log.d("DEBUG", message)
        }
    }
}