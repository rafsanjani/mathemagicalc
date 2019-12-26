package com.foreverrafs.core

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OdeResult(var x: Double, var y: Double, var n: Double) : Parcelable