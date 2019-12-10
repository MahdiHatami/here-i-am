package com.tuga.konum.view.ui.main.people

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tuga.konum.R

class PeopleFragment : Fragment() {

  private val callback = OnMapReadyCallback { googleMap ->
    val sydney = LatLng(38.3554, 38.3335)
    googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_people, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setHasOptionsMenu(true)
    val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
    mapFragment?.getMapAsync(callback)
  }
}