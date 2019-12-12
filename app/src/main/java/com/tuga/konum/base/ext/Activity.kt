package com.tuga.konum.base.ext

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tuga.konum.base.keyboard.FluidContentResizer

fun Activity.actAsFluid() = FluidContentResizer.listen(this)

fun Activity.toast(message: String?) =
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

