package com.aby.capstone_quasars_bobal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.aby.capstone_quasars_bobal.R;
import com.aby.capstone_quasars_bobal.database.SpeakingTest;

import java.util.ArrayList;
import java.util.List;

public class TestListerAdapter extends RecyclerView.Adapter<TestListerAdapter.TestsViewHolder>  {

    Context context;
    List<SpeakingTest> noteList = new ArrayList<>();
    private List<SpeakingTest> noteListFull = new ArrayList<>();

    private OnItemClickListner listner;


    public TestListerAdapter(Context context, List<SpeakingTest> noteList) {
        this.context = context;
        this.noteList = noteList;
        noteListFull = new ArrayList<>(noteList);

    }

    public SpeakingTest getNoteAt(int position){
        return noteList.get(position);
    }

    @Override
    public TestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.test_single_item,parent,false);
        TestsViewHolder nvh = new TestsViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(TestsViewHolder holder, int position) {
        holder.name.setText(noteList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }


    public class TestsViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        public TestsViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.test_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position  =  getAdapterPosition();

                    if(listner != null && position != RecyclerView.NO_POSITION){
                        listner.onItemClick(noteList.get(position));
                    }

                }
            });

        }
    }

    public  interface  OnItemClickListner {
        void onItemClick(SpeakingTest contact);
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner){
        this.listner = onItemClickListner;
    }

}
