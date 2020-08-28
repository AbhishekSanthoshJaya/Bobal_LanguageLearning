package com.aby.capstone_quasars_bobal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aby.capstone_quasars_bobal.adapter.ArticleAdapterSmall;
import com.aby.capstone_quasars_bobal.adapter.TestListerAdapter;
import com.aby.capstone_quasars_bobal.adapter.TestListerAdapterSmall;
import com.aby.capstone_quasars_bobal.database.LocalCacheManager;
import com.aby.capstone_quasars_bobal.database.SpeakingTest;
import com.aby.capstone_quasars_bobal.interfaces.MainViewInterface;
import com.aby.capstone_quasars_bobal.model.Article;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements ArticleAdapterSmall.OnArticleListener, MainViewInterface {



    @BindView(R.id.rv_artticles)
    public RecyclerView recyclerView;

    @BindView(R.id.rv_test)
    public RecyclerView recyclerViewTests;

    @BindView(R.id.see_more_articles)
    public TextView seeMoreArticles;

    @BindView(R.id.see_more_tests)
    public TextView seeMoreTests;

    @BindView(R.id.flashcard)
    public TextView flashcard;


    @BindView(R.id.word_of_the_moment)
    public TextView wordTxtView;





    private ArticleAdapterSmall articleAdapter;
    private TestListerAdapterSmall testListerAdapter;

    private ArrayList<Article> articles;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        getSupportActionBar().hide();

        seeMoreArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ArticleListerActivity.class);
                startActivity(intent);
            }


        });


        seeMoreTests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,TestListerActivity.class);
                startActivity(intent);
            }
        });

        flashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,FlashCardActivity.class);
                startActivity(intent);
            }
        });


        String[] texts= {"anomaly (noun) \n something that is unusual or unexpected",
        "equivocal (adj.) \n not easily understood or explained ",
                "lucid (adj.) \n very clear and easy to understand ",
                "assuage (verb) \n to make (an unpleasant feeling) less intense"
        };
        Random r = new Random();
        wordTxtView.setText(texts[r.nextInt(texts.length)]);


        ArrayList<String> qs1 = new ArrayList<String>();
        qs1.add("What do you do in Free time?");
        qs1.add("Does it make you happy? If so why?");
        qs1.add("Do you participate with your family?");
        qs1.add("Tell me about you past experience.");
        LocalCacheManager.getInstance(this).addTests(this,new SpeakingTest('1',"Hobby",qs1));

        ArrayList<String> qs2 = new ArrayList<String>();
        qs2.add("What sports do you play?");
        qs2.add("Is it hard to learn?");
        qs2.add("What do you suggest for complete beginner");
        qs2.add("Have you ever won something in this sport?");
        LocalCacheManager.getInstance(this).addTests(this,new SpeakingTest('2',"Sports",qs2));

        ArrayList<String> qs3 = new ArrayList<String>();
        qs3.add("Tell me about you family.");
        qs3.add("Do you enjoy time with family?");
        qs3.add("Should people go for family vacation often?");
        qs3.add("Who is you role model in family and why?");
        LocalCacheManager.getInstance(this).addTests(this,new SpeakingTest('3',"family",qs3));


        recyclerViewTests.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        LocalCacheManager.getInstance(this).getTests(this);


        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        articles = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);


        articleAdapter = new ArticleAdapterSmall(HomeActivity.this,articles, requestQueue,HomeActivity.this);
        recyclerView.setAdapter(articleAdapter);

        parseArticle();
    }

    private void parseArticle(){
        String url = "http://142.93.156.57:8983/solr/article_search/select?rows=5&q=canada";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("response");

                            JSONArray jsonArray = jsonObject.getJSONArray("docs");




                            for(int i =0 ; i<jsonArray.length(); i++){
                                JSONObject doc = jsonArray.getJSONObject(i);
                                String title = doc.getString("title");
                                String imageUrl = doc.getString("url_to_image");
                                String sourceUrl = doc.getString("url");
                                String source_name = doc.getString("source_name");
                                String author = doc.getString("author");
                                String publishedat = doc.getString("publishedat");
                                String key = doc.getString("key");

                                String finalHl = "";



                                articles.add(new Article(imageUrl,title,sourceUrl,source_name,
                                        author,publishedat,finalHl));
                            }

                            articleAdapter = new ArticleAdapterSmall(HomeActivity.this,articles, requestQueue, HomeActivity.this);
                            recyclerView.setAdapter(articleAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onArticleClick(int position) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);

        intent.putExtra("url",articles.get(position).getSourceUrl());
        startActivity(intent);
    }

    @Override
    public void onTestLoaded(List<SpeakingTest> notesList) {

        if(notesList.size() == 0){
            onDataNotAvailable();
        }else {
            testListerAdapter = new TestListerAdapterSmall(this, notesList.subList(0,2));
            testListerAdapter.setOnItemClickListner(new TestListerAdapterSmall.OnItemClickListner() {
                @Override
                public void onItemClick(SpeakingTest note) {
                    Intent intent = new Intent(HomeActivity.this,SpeakingTestActivity.class);
                    intent.putExtra("test",note);
                    startActivity(intent);
                }
            });
            recyclerViewTests.setAdapter(testListerAdapter);
        }
    }

    @Override
    public void onTestAdded() {

        recyclerViewTests.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        LocalCacheManager.getInstance(this).getTests(this);

    }

    @Override
    public void onDataNotAvailable() {

    }
}