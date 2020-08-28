package com.aby.capstone_quasars_bobal;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aby.capstone_quasars_bobal.adapter.AudioListerAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestAudioListerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestAudioListerFragment extends Fragment implements AudioListerAdapter.OnItemClick {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ConstraintLayout constraintLayout;
    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView recyclerView;
    private AudioListerAdapter audioListerAdapter;

    private File[] files;

    private MediaPlayer mediaPlayer = null;
    private boolean isPlaying = false;
    private File fileCurrent;

    public TestAudioListerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TestAudioListerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestAudioListerFragment newInstance(String param1, String param2) {
        TestAudioListerFragment fragment = new TestAudioListerFragment();
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
        return inflater.inflate(R.layout.fragment_test_audio_lister, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        constraintLayout = view.findViewById(R.id.player_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState== BottomSheetBehavior.STATE_HIDDEN){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        recyclerView = view.findViewById(R.id.audio_list_view);
        String path = getActivity().getExternalFilesDir("/").getAbsolutePath();
        File directory = new File(path);
        files = directory.listFiles();

        audioListerAdapter = new AudioListerAdapter(files, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(audioListerAdapter);
    }

    @Override
    public void onClickListner(File file, int position) {

        if(isPlaying){
            stopAudio();
            isPlaying = !isPlaying;
            playAudio(fileCurrent);

        }
        else{
            fileCurrent = file;
            playAudio(fileCurrent);
            isPlaying = !isPlaying;

        }
    }

    private void stopAudio() {
        isPlaying = false;
    }

    private void playAudio(File fileCurrent) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(fileCurrent.getAbsolutePath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
        isPlaying = true;
    }
}