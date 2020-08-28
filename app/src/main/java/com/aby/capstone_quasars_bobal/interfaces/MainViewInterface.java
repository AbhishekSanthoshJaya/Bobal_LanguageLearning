package com.aby.capstone_quasars_bobal.interfaces;


import com.aby.capstone_quasars_bobal.database.SpeakingTest;

import java.util.List;

public interface MainViewInterface {

    void onTestLoaded(List<SpeakingTest> speakingTests);

    void onTestAdded();

    void onDataNotAvailable();

}
