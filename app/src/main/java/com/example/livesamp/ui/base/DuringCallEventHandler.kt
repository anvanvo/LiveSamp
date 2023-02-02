package com.example.livesamp.ui.base

import com.example.livesamp.constants.Constant

interface DuringCallEventHandler {

    fun onUserJoined(uid: Int)

    fun onFirstRemoteVideoDecoded(uid: Int, width: Int, height: Int, elapsed: Int)

    fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int)

    fun onUserOffline(uid: Int, reason: Int)

    fun onExtraCallback(type: Constant.AGEventHandler, vararg data: Any)
}