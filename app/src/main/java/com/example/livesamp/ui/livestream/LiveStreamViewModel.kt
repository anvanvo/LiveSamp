package com.example.livesamp.ui.livestream

import com.example.livesamp.ui.base.BaseViewModel

class LiveStreamViewModel : BaseViewModel() {
    var isAudienceRole = true
    var channelName = ""
    var uid = -1 // ID yourself unique in the channel.
    var token = ""
}