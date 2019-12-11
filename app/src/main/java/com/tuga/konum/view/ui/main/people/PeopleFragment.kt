package com.tuga.konum.view.ui.main.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tuga.konum.R
import com.tuga.konum.databinding.FragmentPeopleBinding
import dagger.android.support.DaggerFragment
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
    val sydney = LatLng(38.3554, 38.3335)
    googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
  }

}
