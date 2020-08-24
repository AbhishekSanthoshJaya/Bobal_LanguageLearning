package com.aby.capstone_quasars_bobal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.aby.capstone_quasars_bobal.adapter.ArticleAdapter;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleListerActivity extends AppCompatActivity {


    @BindView(R.id.articleRecyclerView)
    public RecyclerView recyclerView;

    private ArticleAdapter articleAdapter;
    private ArrayList<Article> articles;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_lister);

        ButterKnife.bind(this);


        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        articles = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseArticle();
    }

    private void parseArticle(){
        String url = "http://142.93.156.57:8983/solr/article_search/select?q=entrenched";

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

                                articles.add(new Article(imageUrl,title,sourceUrl));
                            }

                            articleAdapter = new ArticleAdapter(ArticleListerActivity.this,articles);
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
}