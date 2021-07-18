package com.praman.tambolastats;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mLetterNames = new ArrayList<>();

    public RecyclerViewAdapter(Context context, ArrayList<String> mLetterNames) {
        this.mLetterNames = mLetterNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder: called");
        holder.letterName.setText(mLetterNames.get(position));
    }

    @Override
    public int getItemCount() {
        return mLetterNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView letterName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            letterName = itemView.findViewById(R.id.letterName);

        }
    }
}
