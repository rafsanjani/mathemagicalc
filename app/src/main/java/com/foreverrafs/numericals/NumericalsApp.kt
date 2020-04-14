package com.foreverrafs.numericals

import android.app.Application
import android.os.StrictMode


/* Created by Rafsanjani on 14/04/2020. */

class NumericalsApp  : Application(){
    override fun onCreate() {
        super.onCreate()
        enableStrictMode()
    }
    private fun enableStrictMode() {
        if (BuildConfig.DEBUG) {
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

}