package com.tuga.konum.view.ui.main.people

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.SparseArray
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
import com.google.android.gms.maps.model.BitmapDescriptor
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
    val malatya = LatLng(38.3754, 38.3335)
//    googleMap.addMarker(MarkerOptions().position(malatya).title("Marker in Sydney"))

    val peopleMarker = PeopleMarker(requireContext())
    if (VERSION.SDK_INT >= VERSION_CODES.O) {
      peopleMarker.getBitmapFromView(
        R.layout.custom_marker_layout,
        R.id.marker_user_icon
      ) { bitmap ->
        MarkerOptions()
          .position(malatya)
          .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
      }
    }

//    googleMap.addMarker(
//      MarkerOptions()
//        .position(malatya)
//        .icon(
//          BitmapDescriptorFactory.fromBitmap(
//            createCustomMarker(requireActivity(), R.drawable.avatar_2)
//          )
//        )
//    ).title = "iPragmatech Solutions Pvt Lmt"
//
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(malatya, 15f))

  }

  private fun createCustomMarker(
    context: Context, @DrawableRes resource: Int
  ): Bitmap? {
    val marker: View =
      (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        .inflate(R.layout.custom_marker_layout, null)
    val markerImage = marker.findViewById<View>(R.id.marker_user_icon) as CircleImageView
    markerImage.setImageResource(resource)
    marker.layoutParams = LayoutParams(50, LayoutParams.WRAP_CONTENT)
    marker.measure(0, 0)
    marker.layout(0, 0, 0, 0)
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
