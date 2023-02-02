package com.example.livesamp.ui.base;

import io.agora.rtc2.IRtcEngineEventHandler;

public interface BeforeCallEventHandler{
    void onLastmileQuality(int quality);

    void onLastmileProbeResult(IRtcEngineEventHandler.LastmileProbeResult result);
}
