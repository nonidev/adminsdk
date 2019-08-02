package com.test.adminconsole.helper

import android.util.Log
import com.test.adminconsole.BuildConfig

object LogHelper {
    private var TAG = javaClass.simpleName

    internal fun verbose(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Log.v(tag, msg)
    }

    internal fun debug(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Log.d(tag, msg)
    }

    internal fun info(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Log.i(tag, msg)
    }

    internal fun warn(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Log.w(tag, msg)
    }

    internal fun error(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Log.e(tag, msg)
    }
}