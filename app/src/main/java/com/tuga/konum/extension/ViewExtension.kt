package com.tuga.konum.extension

import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.tuga.konum.Event
import com.tuga.konum.R
import com.tuga.konum.util.EspressoIdlingResource

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

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int) {
  Snackbar.make(this, snackbarText, timeLength).run {
    addCallback(object : Snackbar.Callback() {
      override fun onShown(sb: Snackbar?) {
        EspressoIdlingResource.increment()
      }

      override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
        EspressoIdlingResource.decrement()
      }
    })
    show()
  }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
  lifecycleOwner: LifecycleOwner,
  snackbarEvent: LiveData<Event<Int>>,
  timeLength: Int
) {

  snackbarEvent.observe(lifecycleOwner, Observer { event ->
    event.getContentIfNotHandled()?.let {
      showSnackbar(context.getString(it), timeLength)
    }
  })
}
