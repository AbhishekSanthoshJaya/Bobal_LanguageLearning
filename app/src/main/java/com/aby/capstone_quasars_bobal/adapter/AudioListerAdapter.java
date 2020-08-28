package com.aby.capstone_quasars_bobal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aby.capstone_quasars_bobal.R;
import com.aby.capstone_quasars_bobal.TestReply;
import com.aby.capstone_quasars_bobal.database.TestTaken;

import java.io.File;
import java.util.ArrayList;

public class AudioListerAdapter extends RecyclerView.Adapter<AudioListerAdapter.AudioViewHolder> {

//    private File[] files;
    private ArrayList<TestReply> files;
    private OnItemClick onItemClick;
    public AudioListerAdapter(ArrayList<TestReply> files, OnItemClick onItemClick){

        this.files = files;
        this.onItemClick = onItemClick;
    }
    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_audio_item,parent,false);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {

        holder.fileName.setText(files.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class AudioViewHolder  extends  RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView fileName;
        public AudioViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.list_image_view);
            fileName = itemView.findViewById(R.id.list_file_name);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemClick.onClickListner(files.get(getAdapterPosition()),getAdapterPosition());
        }
    }

    public interface OnItemClick{
        void onClickListner(TestReply file, int position);
    }
}
