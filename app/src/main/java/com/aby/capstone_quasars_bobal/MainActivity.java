package com.aby.capstone_quasars_bobal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.aby.capstone_quasars_bobal.database.LocalCacheManager;
import com.aby.capstone_quasars_bobal.database.SpeakingTest;
import com.aby.capstone_quasars_bobal.interfaces.MainViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.core.view.ViewCompat.getTransitionName;

public class MainActivity extends AppCompatActivity implements MainViewInterface {


    @BindView(R.id.cv_flash_card)
    CardView buttonFlashCard;

    @BindView(R.id.cv_articles)
    CardView buttonArticles;

    @BindView(R.id.cv_speakingtest)
    CardView buttonSpeaking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<String> qs1 = new ArrayList<String>();
        qs1.add("q1");
        qs1.add("q2");
        qs1.add("q3");
        qs1.add("q4");
        LocalCacheManager.getInstance(this).addTests(this,new SpeakingTest('1',"ttestt1",qs1));

        ArrayList<String> qs2 = new ArrayList<String>();
        qs2.add("q1");
        qs2.add("q2");
        qs2.add("q3");
        qs2.add("q4");
        LocalCacheManager.getInstance(this).addTests(this,new SpeakingTest('2',"Test2",qs2));

        ArrayList<String> qs3 = new ArrayList<String>();
        qs3.add("q1");
        qs3.add("q2");
        qs3.add("q3");
        qs3.add("q4");
        LocalCacheManager.getInstance(this).addTests(this,new SpeakingTest('3',"Test3",qs3));


        ButterKnife.bind(this);

    }

    @OnClick(R.id.cv_flash_card)
    public void setButtonFlashCard(){
        Intent intent = new Intent(MainActivity.this,FlashCardActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cv_articles)
    public void setButtonArticles(){
        Intent intent = new Intent(MainActivity.this,ArticleListerActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cv_speakingtest)
    public void setButtonSpeaking()
    {
//        Intent intent = new Intent(MainActivity.this,SpeakingTestActivity.class);
//        startActivity(intent);

        Intent intent = new Intent(MainActivity.this,TestListerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onTestLoaded(List<SpeakingTest> speakingTests) {

    }

    @Override
    public void onTestAdded() {
        System.out.println("test added");
    }

    @Override
    public void onDataNotAvailable() {

    }
}