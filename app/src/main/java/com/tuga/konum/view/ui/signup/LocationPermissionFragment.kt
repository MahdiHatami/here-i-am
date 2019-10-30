package com.tuga.konum.view.ui.signup

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tuga.konum.EventObserver
import com.tuga.konum.OpenForTesting
import com.tuga.konum.R
import com.tuga.konum.binding.FragmentDataBindingComponent
import com.tuga.konum.databinding.FragmentLocationPermissionBinding
import com.tuga.konum.di.Injectable
import com.tuga.konum.permission.PermissionManager
import com.tuga.konum.util.autoCleared
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

@OpenForTesting
class LocationPermissionFragment : Fragment(), Injectable {

  private val REQUEST_CODE_ACCESS_FINE_LOCATION: Int = 100

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  var binding by autoCleared<FragmentLocationPermissionBinding>()

  var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

  private val viewModel: SignupActivityViewModel by viewModels {
    viewModelFactory
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_location_permission,
      container,
      false,
      dataBindingComponent
    )
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    binding.viewModel = viewModel

    viewModel.setLocationPermissionStatus(
      PermissionManager().getPermissionStatus(
        activity!!,
        android.Manifest.permission.ACCESS_FINE_LOCATION
      )
    )

    viewModel.requestLocationPermissionEvent.observe(this, EventObserver {
      ActivityCompat.requestPermissions(
        activity!!,
        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
        REQUEST_CODE_ACCESS_FINE_LOCATION
      )
    })
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.lifecycleOwner = this
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    when (requestCode) {
      REQUEST_CODE_ACCESS_FINE_LOCATION -> {
        if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
          toast("permission granted")
        } else {
          toast("permission denied")
        }
        viewModel.setLocationPermissionStatus(
          PermissionManager().getPermissionStatus(
            activity!!,
            android.Manifest.permission.ACCESS_FINE_LOCATION
          )
        )

      }

      else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
  }

}








