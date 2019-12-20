package com.tuga.konum.view.ui.main.people

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.annotation.DrawableRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentPeopleBinding
import dagger.android.support.DaggerFragment
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject


class PeopleFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel by viewModels<PeopleViewModel> { viewModelFactory }

  private lateinit var binding: FragmentPeopleBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_people, container, false)
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.lifecycleOwner = this

    setHasOptionsMenu(true)
    val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
    mapFragment?.getMapAsync(callback)
  }

  private val callback = OnMapReadyCallback { googleMap ->
    val malatya = LatLng(38.3554, 38.3335)
//    googleMap.addMarker(MarkerOptions().position(malatya).title("Marker in Sydney"))

    googleMap.addMarker(
      MarkerOptions()
        .position(malatya)
        .icon(
          BitmapDescriptorFactory.fromBitmap(
            createCustomMarker(requireActivity(), R.drawable.avatar_0, "Manish")
          )
        )
    ).title = "iPragmatech Solutions Pvt Lmt";

    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(malatya, 15f))

  }

  private fun createCustomMarker(
    context: Context, @DrawableRes resource: Int, _name: String?
  ): Bitmap? {
    val marker: View =
      (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
        R.layout.custom_marker_layout,
        null
      )
    val markerImage =
      marker.findViewById<View>(R.id.user_dp) as CircleImageView
    markerImage.setImageResource(resource)
    val displayMetrics = DisplayMetrics()
    (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
    marker.layoutParams = LayoutParams(52, LayoutParams.WRAP_CONTENT)
    marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
    marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
    marker.buildDrawingCache()
    val bitmap: Bitmap = Bitmap.createBitmap(
      marker.measuredWidth,
      marker.measuredHeight,
      Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    marker.draw(canvas)
    return bitmap
  }

}
