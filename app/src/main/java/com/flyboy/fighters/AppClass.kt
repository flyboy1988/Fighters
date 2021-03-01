package com.flyboy.fighters

import android.app.Application
import com.vosouq.vsq.utils.CrashReportingTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AppClass :Application(){



override fun onCreate(){
    super.onCreate()

    if (BuildConfig.DEBUG) Timber.plant(object : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String? {
            return "${element.className} -> method:${element.methodName} -> ${element.lineNumber}: "
        }
    })
    else Timber.plant(CrashReportingTree())
}
}