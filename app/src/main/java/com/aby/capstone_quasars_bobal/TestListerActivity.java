package com.aby.capstone_quasars_bobal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aby.capstone_quasars_bobal.adapter.TestListerAdapter;
import com.aby.capstone_quasars_bobal.database.LocalCacheManager;
import com.aby.capstone_quasars_bobal.database.SpeakingTest;
import com.aby.capstone_quasars_bobal.interfaces.MainViewInterface;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.core.view.ViewCompat.getTransitionName;

public class TestListerActivity extends AppCompatActivity implements MainViewInterface {

    @BindView(R.id.tests_recycle_view)
    RecyclerView recyclerView;

    TestListerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_lister);
        getSupportActionBar().hide();


        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LocalCacheManager.getInstance(this).getTests(this);


    }

    @Override
    public void onTestLoaded(List<SpeakingTest> notesList) {

        if(notesList.size() == 0){
            onDataNotAvailable();
        }else {
            adapter = new TestListerAdapter(this, notesList);
            adapter.setOnItemClickListner(new TestListerAdapter.OnItemClickListner() {
                @Override
                public void onItemClick(SpeakingTest note) {
                    Intent intent = new Intent(TestListerActivity.this,SpeakingTestActivity.class);
                    intent.putExtra("test",note);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onTestAdded() {



    }

    @Override
    public void onDataNotAvailable() {

    }
}