package com.aby.capstone_quasars_bobal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aby.capstone_quasars_bobal.ArticleListerActivity;
import com.aby.capstone_quasars_bobal.R;
import com.aby.capstone_quasars_bobal.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {


    private Context context;
    private ArrayList<Article> articles;


    public ArticleAdapter(Context context, ArrayList<Article> articles){
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        Article currentArticle = articles.get(position);
        holder.textViewTitle.setText(currentArticle.getTitle());
        Picasso.get().load(currentArticle.getImageUrl()).fit().centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textViewTitle;
        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.article_image_view);
            textViewTitle  = itemView.findViewById(R.id.article_titile_text_view);
        }
    }
}
