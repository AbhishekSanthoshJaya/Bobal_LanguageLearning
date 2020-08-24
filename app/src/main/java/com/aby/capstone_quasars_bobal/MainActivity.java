package com.aby.capstone_quasars_bobal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.core.view.ViewCompat.getTransitionName;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.button_flashcard)
    Button buttonFlashCard;

    @BindView(R.id.button_articles)
    Button buttonArticles;

    @BindView(R.id.button_speaking)
    Button buttonSpeaking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button_flashcard)
    public void setButtonFlashCard(){
        Intent intent = new Intent(MainActivity.this,FlashCardActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_articles)
    public void setButtonArticles(){
        Intent intent = new Intent(MainActivity.this,ArticleListerActivity.class);
        startActivity(intent);
    }


}