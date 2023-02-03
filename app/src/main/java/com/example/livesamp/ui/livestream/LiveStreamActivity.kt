package com.example.livesamp.ui.livestream

import android.content.Context
import android.content.Intent
import android.view.SurfaceView
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.example.livesamp.BuildConfig
import com.example.livesamp.constants.Constant
import com.example.livesamp.databinding.ActivityLiveStreamBinding
import com.example.livesamp.shared.utilities.LogUtils
import com.example.livesamp.ui.base.BaseMVVMActivity
import com.example.livesamp.ui.base.DuringCallEventHandler
import com.example.livesamp.ui.base.RtcEventHandler
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.video.VideoCanvas

class LiveStreamActivity : BaseMVVMActivity<ActivityLiveStreamBinding, LiveStreamViewModel>(), DuringCallEventHandler {

    companion object {
        private const val EXTRA_BUNDLE_JOIN_LIVESTREAM = "extra-bundle-join-livestream"
        private const val EXTRA_CHANNEL = "extra-channel"
        private const val EXTRA_UID = "extra-uid"
        private const val EXTRA_TOKEN = "extra-token"
        private const val EXTRA_ROLE = "extra-role"

        fun startLiveStreamScreen(context: Context?, channel: String, uid: Int, token: String, isAudienceRole: Boolean) {
            val intent = Intent(context, LiveStreamActivity::class.java)
            intent.putExtra(
                EXTRA_BUNDLE_JOIN_LIVESTREAM,
                bundleOf(
                    EXTRA_CHANNEL to channel,
                    EXTRA_UID to uid,
                    EXTRA_TOKEN to token,
                    EXTRA_ROLE to isAudienceRole
                )
            )
            context?.startActivity(intent)
        }
    }

    private val rtcEventHandler = RtcEventHandler()

    private val mRtcEngine by lazy {
        RtcEngine.create(this, BuildConfig.APP_ID_AGORA, rtcEventHandler).apply {
            rtcEventHandler.addEventHandler(this@LiveStreamActivity)
            enableVideo()
        }
    }

    override fun getViewModelClass() = LiveStreamViewModel::class.java

    override fun getLayoutBinding() = ActivityLiveStreamBinding.inflate(layoutInflater)

    override fun initialize() {
        intent.extras?.getBundle(EXTRA_BUNDLE_JOIN_LIVESTREAM)?.let {
            viewModel.channelName = it.getString(EXTRA_CHANNEL, "")
            viewModel.uid = it.getInt(EXTRA_UID, -1)
            viewModel.token = it.getString(EXTRA_TOKEN, "")
            viewModel.isAudienceRole = it.getBoolean(EXTRA_ROLE)
        }
        startJoinChannel(viewModel.token)
    }

    override fun registerViewEvent() {
        viewBinding.btnExitStream.setOnClickListener { finish() }
    }

    override fun registerViewModelObs() {
        // DO NOTHING
    }

    override fun onDestroy() {
        super.onDestroy()
        mRtcEngine.stopPreview()
        mRtcEngine.leaveChannel()
        viewBinding.livestreamVideoContainer.removeAllViews()
    }

    private fun setupRemoteVideo(uid: Int) {
        val surfaceView = SurfaceView(this)
        surfaceView.setZOrderMediaOverlay(true)
        viewBinding.livestreamVideoContainer.addView(surfaceView)
        mRtcEngine.setupRemoteVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid))
        surfaceView.visibility = View.VISIBLE
    }

    private fun setupLocalVideo() {
        val surfaceView = SurfaceView(this)
        surfaceView.setZOrderMediaOverlay(true)
        viewBinding.livestreamVideoContainer.addView(surfaceView)
        mRtcEngine.setupLocalVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0))
        surfaceView.visibility = View.VISIBLE
    }

    private fun startJoinChannel(token: String) {
        val options = ChannelMediaOptions()
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
        if (viewModel.isAudienceRole) {
            options.clientRoleType = Constants.CLIENT_ROLE_AUDIENCE
        } else {
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            setupLocalVideo()
            mRtcEngine.startPreview()
        }
        mRtcEngine.joinChannel(token, viewModel.channelName, viewModel.uid, options)
    }

    override fun onUserJoined(uid: Int) {
        LogUtils.logE("onUserJoined uid:$uid")
        lifecycleScope.launchWhenCreated {
            if (viewModel.isAudienceRole) {
                setupRemoteVideo(uid)
            }
        }
    }

    override fun onFirstRemoteVideoDecoded(uid: Int, width: Int, height: Int, elapsed: Int) {
        LogUtils.logE("onFirstRemoteVideoDecoded uid:$uid")
    }

    override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
        LogUtils.logE("onJoinChannelSuccess channel:$channel uid:$uid")
    }

    override fun onUserOffline(uid: Int, reason: Int) {
        LogUtils.logE("onUserOffline uid:$uid reason:$reason")
    }

    override fun onExtraCallback(type: Constant.AGEventHandler, vararg data: Any) {
        LogUtils.logE("onExtraCallback type:$type data:$data")
    }
}