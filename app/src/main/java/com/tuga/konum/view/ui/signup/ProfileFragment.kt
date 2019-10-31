package com.tuga.konum.view.ui.signup

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.theartofdev.edmodo.cropper.CropImage
import com.tuga.konum.EventObserver
import com.tuga.konum.R
import com.tuga.konum.binding.FragmentDataBindingComponent
import com.tuga.konum.databinding.FragmentProfileBinding
import com.tuga.konum.event.RequestGalleryImagePicker
import com.tuga.konum.event.RequestStoragePermissionEvent
import com.tuga.konum.extension.onTextChanged
import com.tuga.konum.permission.PermissionManager
import com.tuga.konum.util.autoCleared
import dagger.android.support.DaggerFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class ProfileFragment :  DaggerFragment() {

  private val REQUEST_CODE_READ_EXTERNAL_STORAGE: Int = 100

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  var binding by autoCleared<FragmentProfileBinding>()

  var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

  private val viewModel: SignupActivityViewModel by viewModels {
    viewModelFactory
  }

  private val args: PasswordFragmentArgs by navArgs()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_profile,
      container,
      false,
      dataBindingComponent
    )

    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    binding.viewModel = viewModel

    viewModel.signupCompletedEvent.observe(this, EventObserver {
      navController()
        .navigate(ProfileFragmentDirections.actionProfileFragmentToLocationPermissionFragment())
    })

    viewModel.setStoragePermissionStatus(
      PermissionManager().getPermissionStatus(
        activity!!,
        Manifest.permission.READ_EXTERNAL_STORAGE
      )
    )
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding.lifecycleOwner = this

    val user = args.user
    viewModel.setUser(user)

    binding.edtUsername.onTextChanged {
      viewModel.onUsernameChanged(it.toString())
    }
  }

  override fun onStart() {
    super.onStart()
    EventBus.getDefault().register(this)
  }

  override fun onStop() {
    super.onStop()
    EventBus.getDefault().unregister(this)
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
      val result = CropImage.getActivityResult(data)
      if (resultCode == RESULT_OK) {
        val resultUri = result.uri.path
        val bitmap = if (android.os.Build.VERSION.SDK_INT >= 29) {
          val file = File(resultUri)
          ImageDecoder.decodeBitmap(ImageDecoder.createSource(file))
        } else {
          MediaStore.Images.Media.getBitmap(activity!!.contentResolver, result.uri)
        }
        viewModel.userProfileImagePath.postValue(resultUri!!)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
        viewModel.getUser().image = encoded
      } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        val error = result.error
      }
    }
  }

  @Subscribe
  fun onRequestStoragePermissionEvent(event: RequestStoragePermissionEvent) {
    ActivityCompat.requestPermissions(
      activity!!,
      arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
      REQUEST_CODE_READ_EXTERNAL_STORAGE
    )
  }

  @Subscribe
  fun onRequestGalleryImagePicker(event: RequestGalleryImagePicker) {
    CropImage.activity().start(activity!!, this);
  }

  companion object {
    private const val TAG = "ProfileFragment"
  }

  fun navController() = findNavController()
}
