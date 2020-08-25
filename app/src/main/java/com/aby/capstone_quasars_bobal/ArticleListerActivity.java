package com.aby.capstone_quasars_bobal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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


        articleAdapter = new ArticleAdapter(ArticleListerActivity.this,articles, requestQueue);
        recyclerView.setAdapter(articleAdapter);

        parseArticle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_lister_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.article_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                if(newText.isEmpty()){
                    newText= "*:*";
                }
                String url = "http://142.93.156.57:8983/solr/article_search/select?rows=20&q="+newText;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject("response");

                                    JSONArray jsonArray = jsonObject.getJSONArray("docs");

                                    articles.clear();
                                    for(int i =0 ; i<jsonArray.length(); i++){
                                        JSONObject doc = jsonArray.getJSONObject(i);
                                        String title = doc.getString("title");
                                        String imageUrl = doc.getString("url_to_image");
                                        String sourceUrl = doc.getString("url");

                                        articles.add(new Article(imageUrl,title,sourceUrl));
                                    }

                                    articleAdapter = new ArticleAdapter(ArticleListerActivity.this,articles, requestQueue);
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


                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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

                            articleAdapter = new ArticleAdapter(ArticleListerActivity.this,articles, requestQueue);
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