package com.tuga.konum.view.ui.signup

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mlsdev.rximagepicker.RxImageConverters
import com.mlsdev.rximagepicker.RxImagePicker
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
import java.io.File

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
        // todo create user profile and sync to server

        val navController = v.findNavController()
        navController.navigate(
          ProfileFragmentDirections.actionProfileFragmentToLocationPermissionFragment()
        )
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
    RxImagePicker.with(activity).requestImage(event.source).flatMap { uri ->
      RxImageConverters.uriToFile(activity, uri, File.createTempFile("image", ".jpg"))
    }.subscribe {
      imageSelected.show()
      Glide.with(this).load(it).thumbnail().into(imageSelected)
      mFile = it
    }.dispose()
  }
}
