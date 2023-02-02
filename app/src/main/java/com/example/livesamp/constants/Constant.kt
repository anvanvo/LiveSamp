package com.example.livesamp.constants

object Constant {

    enum class AGEventHandler(eventCode: Int) {
        EVENT_TYPE_ON_DATA_CHANNEL_MSG(3),
        EVENT_TYPE_ON_USER_VIDEO_MUTED(6),
        EVENT_TYPE_ON_USER_AUDIO_MUTED(7),
        EVENT_TYPE_ON_SPEAKER_STATS(8),
        EVENT_TYPE_ON_AGORA_MEDIA_ERROR(9),
        EVENT_TYPE_ON_USER_VIDEO_STATS(10),
        EVENT_TYPE_ON_APP_ERROR(13),
        EVENT_TYPE_ON_AUDIO_ROUTE_CHANGED(18)
    }

    enum class AppError(code: Int) {
        NO_CONNECTION_ERROR(3),
    }
}