package com.example.livesamp.ui

import android.Manifest
import com.example.livesamp.databinding.ActivityMainBinding
import com.example.livesamp.ui.base.BaseMVVMActivity
import com.example.livesamp.ui.livestream.LiveStreamActivity

class MainActivity : BaseMVVMActivity<ActivityMainBinding, MainViewModel>() {

    companion object {
        private const val BASE_VALUE_PERMISSION = 0X0001
        const val PERMISSION_REQ_ID_RECORD_AUDIO = BASE_VALUE_PERMISSION + 1
        const val PERMISSION_REQ_ID_CAMERA = BASE_VALUE_PERMISSION + 2
    }

    override fun getViewModelClass() = MainViewModel::class.java

    override fun getLayoutBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initialize() {
        // DO NOTHING
    }

    override fun registerViewEvent() {
        viewBinding.btnJoinRoom.setOnClickListener {
            val channel = viewBinding.etChannelName.text.toString()
            val uid = viewBinding.etUid.text.toString().toIntOrNull()
            val token = viewBinding.etToken.text.toString()
            val isAudience = viewBinding.scAudience.isChecked

            if (checkSelfPermissions() && uid != null && channel.isNotBlank() && token.isNotBlank()) {
                LiveStreamActivity.startLiveStreamScreen(this, channel, uid, token, isAudience)
            }
        }
    }

    override fun registerViewModelObs() {
        // DO NOTHING
    }

    private fun checkSelfPermissions(): Boolean {
        return checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO) &&
                checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID_CAMERA)
    }
}