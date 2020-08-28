package com.aby.capstone_quasars_bobal.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aby.capstone_quasars_bobal.ArticleListerActivity;
import com.aby.capstone_quasars_bobal.R;
import com.aby.capstone_quasars_bobal.model.Article;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> implements Filterable {


    private Context context;
    private ArrayList<Article> articles;
    private ArrayList<Article> articlesAll;
    private RequestQueue requestQueue;
    private OnArticleListener onArticleListener;

    public ArticleAdapter(Context context, ArrayList<Article> articles, RequestQueue requestQueue, OnArticleListener onArticleListener){
        this.context = context;
        this.articles = articles;
        this.articlesAll = new ArrayList<>(articles);
        this.requestQueue = requestQueue;
        this.onArticleListener = onArticleListener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(v, onArticleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        Article currentArticle = articles.get(position);
        holder.textViewTitle.setText(currentArticle.getTitle());
        holder.sourcename.setText(currentArticle.getSource_name());
        holder.publishedAt.setText(currentArticle.getPublishedat());
        holder.highlight.setText(Html.fromHtml(currentArticle.getHighlighting()));

        Picasso.get().load(currentArticle.getImageUrl()).fit().centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }




    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Article> filteredList = new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                filteredList.addAll(articlesAll);
            }
            else{
                // voley call
                String url = "http://142.93.156.57:8983/solr/article_search/select?q=*:*";

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
                                    System.out.println("response = article count:" + articles.size());
                                    filteredList.addAll(articles);

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

                // end call

            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            articles.clear();
            articles.addAll((Collection<? extends Article>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView textViewTitle, sourcename, publishedAt, highlight;
        OnArticleListener onArticleListener;
        public ArticleViewHolder(@NonNull View itemView, OnArticleListener onArticleListener) {
            super(itemView);
            this.onArticleListener = onArticleListener;
            imageView = itemView.findViewById(R.id.article_image_view);
            textViewTitle  = itemView.findViewById(R.id.article_titile_text_view);
            sourcename = itemView.findViewById(R.id.source_name_textView);
            publishedAt = itemView.findViewById(R.id.published_at_textView);
            highlight = itemView.findViewById(R.id.article_highlight);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onArticleListener.onArticleClick(getAdapterPosition());
        }
    }

    public interface OnArticleListener{
        void onArticleClick(int position);
    }


}
