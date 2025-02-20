package com.tuga.konum.view.ui.signup.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.theartofdev.edmodo.cropper.CropImage
import com.tuga.konum.R
import com.tuga.konum.base.EventObserver
import com.tuga.konum.databinding.FragmentProfileBinding
import com.tuga.konum.extension.onTextChanged
import com.tuga.konum.extension.setupSnackbar
import com.tuga.konum.permission.PermissionManager
import com.tuga.konum.util.BitmapResolver
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProfileFragment : DaggerFragment() {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel by viewModels<ProfileViewModel> { viewModelFactory }

  private lateinit var binding: FragmentProfileBinding

  private val args: ProfileFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
    binding.viewModel = viewModel
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupSnackbar()

    viewModel.signupCompletedEvent.observe(viewLifecycleOwner,
      EventObserver {
        findNavController()
          .navigate(
            ProfileFragmentDirections.actionProfileFragmentToLocationPermissionFragment()
          )
      })

    viewModel.setStoragePermissionStatus(
      PermissionManager().getPermissionStatus(
        activity!!,
        Manifest.permission.READ_EXTERNAL_STORAGE
      )
    )

    viewModel.requestStoragePermissionEvent.observe(viewLifecycleOwner, EventObserver {
      ActivityCompat.requestPermissions(
        activity!!,
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
        REQUEST_CODE_READ_EXTERNAL_STORAGE
      )
    })

    viewModel.requestGalleryImagePicker.observe(viewLifecycleOwner, EventObserver {
      CropImage.activity().start(activity!!, this)
    })
  }

  private fun setupSnackbar() {
    view?.setupSnackbar(this, viewModel.snackbarMessage, Snackbar.LENGTH_SHORT)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.lifecycleOwner = this.viewLifecycleOwner

    val user = args.user
    viewModel.setUser(user)

    binding.edtUsername.onTextChanged {
      viewModel.onUsernameChanged(it.toString())
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    when (requestCode) {
      REQUEST_CODE_READ_EXTERNAL_STORAGE -> viewModel.setStoragePermissionStatus(
        PermissionManager().getPermissionStatus(
          activity!!,
          Manifest.permission.READ_EXTERNAL_STORAGE
        )
      )

      else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      if (resultCode == Activity.RESULT_OK) {
        val result = CropImage.getActivityResult(data)
        val capturedImage = BitmapResolver.getBitmap(activity!!.contentResolver, result.uri)
        viewModel.onActivityResultImagePick(resultCode, result.uri.path, capturedImage)
      }
    }
  }

  companion object {
    private const val REQUEST_CODE_READ_EXTERNAL_STORAGE: Int = 100
  }
}
