package com.tuga.konum.view.ui.signup

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.theartofdev.edmodo.cropper.CropImage
import com.tuga.konum.R
import com.tuga.konum.compose.ViewModelFragment
import com.tuga.konum.databinding.FragmentProfileBinding
import com.tuga.konum.event.RequestGalleryImagePicker
import com.tuga.konum.event.RequestStoragePermissionEvent
import com.tuga.konum.models.entity.User
import com.tuga.konum.permission.PermissionManager
import kotlinx.android.synthetic.main.fragment_profile.btnProfileNext
import kotlinx.android.synthetic.main.fragment_profile.edtUsername
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import android.app.Activity.RESULT_OK
import android.provider.MediaStore
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import java.io.File
import android.R.attr.bitmap
import java.io.ByteArrayOutputStream
import android.util.Base64

class ProfileFragment : ViewModelFragment(), OnClickListener {
  private val REQUEST_CODE_READ_EXTERNAL_STORAGE: Int = 100

  private val viewModel by viewModel<SignupActivityViewModel>()
  private lateinit var binding: FragmentProfileBinding
  private val args: ProfileFragmentArgs by navArgs()
  private var user: User = User()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    user = args?.user
    Timber.d(user.toString())
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = binding(inflater, R.layout.fragment_profile, container)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    btnProfileNext.setOnClickListener(this)
  }

  override fun onStart() {
    super.onStart()
    EventBus.getDefault().register(this)
    viewModel.setStoragePermissionStatus(
      PermissionManager().getPermissionStatus(
        activity!!,
        Manifest.permission.READ_EXTERNAL_STORAGE
      )
    )
  }

  override fun onStop() {
    super.onStop()
    EventBus.getDefault().unregister(this)
  }

  override fun onClick(v: View) {
    when (v.id) {
      R.id.btnProfileNext -> {
        val username = edtUsername.text.toString()
        user.username = username
        Timber.d(user.toString())
//        val navController = v.findNavController()
//        navController.navigate(
//          ProfileFragmentDirections.actionProfileFragmentToLocationPermissionFragment()
//        )
      }
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
        user.image = encoded
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
}
