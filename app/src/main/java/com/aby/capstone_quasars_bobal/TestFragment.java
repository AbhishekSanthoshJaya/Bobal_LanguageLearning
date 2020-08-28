package com.aby.capstone_quasars_bobal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private NavController navController;
    private ImageButton lstBtn;
    private ImageButton  recordBtn;

    private boolean isRecording = false;
    private MediaRecorder mediaRecorder;
    private Chronometer chronometer;
    private TextView directionText;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String recordFile;

    public TestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(String param1, String param2) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        lstBtn = view.findViewById(R.id.test_audio_lst_button);

        recordBtn = view.findViewById(R.id.speak_button);

        chronometer = view.findViewById(R.id.record_timer);

        directionText = view.findViewById(R.id.direction_text);

        recordBtn.setOnClickListener(this);

        lstBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.test_audio_lst_button:
                navController.navigate(R.id.action_testFragment_to_testAudioListerFragment);
                break;


            case R.id.speak_button:

                if(isRecording){
                    recordBtn.setImageDrawable(getResources().getDrawable(R.drawable.dot,null));
                    stopRecording();
                    
                    isRecording = !isRecording;
                }
                else{
                    if(hasPermission()){
                        recordBtn.setImageDrawable(getResources().getDrawable(R.drawable.record,null));
                        startRecording();
                        isRecording = !isRecording;
                    }

                }

                break;


        }
    }

    private void startRecording() {

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        String filePath = getActivity().getExternalFilesDir("/").getAbsolutePath();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.CANADA);
        Date date = new Date();

        recordFile = "Record_"+simpleDateFormat.format(date)+".3gp";

        directionText.setText("Recording: \n"+recordFile);

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(filePath + "/" + recordFile);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();

    }

    private void stopRecording() {

        chronometer.stop();
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        directionText.setText("Recording Stopped for: \n"+ recordFile);



    }

    private boolean hasPermission() {
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},10002);
        return false;
    }
}