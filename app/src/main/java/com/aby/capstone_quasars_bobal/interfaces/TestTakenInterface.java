package com.aby.capstone_quasars_bobal.interfaces;

import com.aby.capstone_quasars_bobal.database.SpeakingTest;
import com.aby.capstone_quasars_bobal.database.TestTaken;

import java.util.List;

public interface TestTakenInterface {


    void onTestTakenLoaded(List<TestTaken> testTakens);

    void onTestTakenAdded();

    void onDataNotAvailable();
}
