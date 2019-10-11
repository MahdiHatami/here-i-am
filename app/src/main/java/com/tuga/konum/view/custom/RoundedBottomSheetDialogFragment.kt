package com.tuga.konum.view.custom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tuga.konum.R

/**
 * [BottomSheetDialogFragment] that uses a custom
 * theme which sets a rounded background to the dialog
 * and doesn't dim the navigation bar
 */
open class RoundedBottomSheetDialogFragment : BottomSheetDialogFragment() {

  val TAG = "ActionBottomDialog"

  override fun getTheme(): Int = R.style.BottomSheetDialogTheme

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
    BottomSheetDialog(requireContext(), theme)

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.choose_photo_source, container); }

}
