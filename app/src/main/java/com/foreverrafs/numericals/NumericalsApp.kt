package com.foreverrafs.numericals

import android.app.Application
import android.os.StrictMode
import timber.log.Timber


/* Created by Rafsanjani on 14/04/2020. */

class NumericalsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            enableStrictMode()
            enableTimber()
        }
    }

    private fun enableTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun enableStrictMode() {
        val policy = StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()

        StrictMode.setThreadPolicy(policy)

        val vmPolicy = StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()

        StrictMode.setVmPolicy(vmPolicy)
    }
}