package com.praman.tambolastats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<dataModel> dataModelList;

    public RecyclerAdapter(Context context, ArrayList<dataModel> dataModelList) {
        this.dataModelList = dataModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name_tv.setText(dataModelList.get(position).getName());
        holder.remaining_tv.setText(dataModelList.get(position).getRemaining());
        holder.cnt_tv.setText(dataModelList.get(position).getCnt());
    }

    public void updateSimple(ArrayList<dataModel> dataModelList){
        this.dataModelList.clear();
        this.dataModelList.addAll(dataModelList);
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_tv, remaining_tv,cnt_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.name_tv);
            remaining_tv = itemView.findViewById(R.id.remaining_tv);
            cnt_tv = itemView.findViewById(R.id.cnt_tv);
        }
    }

}
