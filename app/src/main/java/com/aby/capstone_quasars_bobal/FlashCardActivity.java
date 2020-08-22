package com.aby.capstone_quasars_bobal;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class FlashCardActivity extends AppCompatActivity {

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;
    TextView txtFront, txtBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        txtFront = findViewById(R.id.txtFront);
        txtBack = findViewById(R.id.txtBack);
        findViews();
        loadAnimations();
        changeCameraDistance();
        //loadJSONFromAsset();
        try {
            parseJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    private void findViews() {
        mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout = findViewById(R.id.card_front);
    }

    public void flipCard(View view) {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("words.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void parseJSON() throws JSONException {
        HashMap<String, String> wordsMap= new HashMap<>();
        String jsonStr = loadJSONFromAsset();
        JSONArray jsonarray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            String word = jsonobject.getString("word");
            String meaning = jsonobject.getString("meaning");
            wordsMap.put(word, meaning);
            txtFront.setText(word);
            txtBack.setText(wordsMap.get(word));
            Log.i("word", word);
            Log.i("meaning", meaning);
            Log.i("map", wordsMap.toString());
        }
    }
}

