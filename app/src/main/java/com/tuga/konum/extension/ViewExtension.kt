package com.tuga.konum.extension

import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.tuga.konum.R

fun View.visible() {
  visibility = View.VISIBLE
}

fun View.requestGlideListener(): RequestListener<Drawable> {
  return object : RequestListener<Drawable> {
    override fun onLoadFailed(
      e: GlideException?,
      model: Any?,
      target: Target<Drawable>?,
      isFirstResource: Boolean
    ): Boolean {
      return false
    }

    override fun onResourceReady(
      resource: Drawable?,
      model: Any?,
      target: Target<Drawable>?,
      dataSource: DataSource?,
      isFirstResource: Boolean
    ): Boolean {
      circularRevealedAtCenter()
      return false
    }
  }
}

fun View.circularRevealedAtCenter() {
  val view = this
  val cx = (view.left + view.right) / 2
  val cy = (view.top + view.bottom) / 2
  val finalRadius = Math.max(view.width, view.height)

  if (checkIsMaterialVersion() && view.isAttachedToWindow) {
    val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius.toFloat())
    view.visible()
    view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.background))
    anim.duration = 550
    anim.start()
  }
}
fun EditText.onTextChanged(action: (CharSequence) -> Unit) {
  addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(string: Editable?) = Unit
    override fun beforeTextChanged(string: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
      action(string ?: "")
    }
  })
}

fun EditText.clearOnTextChangedListener() {
  onTextChanged {}
}
