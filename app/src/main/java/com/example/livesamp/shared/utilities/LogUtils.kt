package com.example.livesamp.shared.utilities

import android.util.Log

object LogUtils {
    private const val TAG = "livesamp"

    fun logE(message: String) {
        Log.e(TAG, message)
    }

    fun logE(throwable: Throwable?) {
        throwable ?: return
        Log.e(TAG, throwable.stackTraceToString())
    }
}
