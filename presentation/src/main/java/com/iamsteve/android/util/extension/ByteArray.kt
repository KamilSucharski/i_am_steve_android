package com.iamsteve.android.util.extension

import android.graphics.BitmapFactory

fun ByteArray.toBitmap() = BitmapFactory.decodeByteArray(this, 0, this.size)