package com.mukeshkpdeveloper.infoenumtask.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mukeshkpdeveloper.infoenumtask.R;
import com.mukeshkpdeveloper.infoenumtask.models.Things;

import java.util.ArrayList;

public class SelectedThingsAdapter extends RecyclerView.Adapter<SelectedThingsAdapter.MyViewHolder> {

    private ArrayList<Things> mThingsList;

    public SelectedThingsAdapter(ArrayList<Things> modelList) {
        mThingsList = modelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.things_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Things model = mThingsList.get(position);
        holder.textView.setText(model.getThingName());
        holder.view.setBackgroundColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return mThingsList == null ? 0 : mThingsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView textView;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textView = itemView.findViewById(R.id.tv_thing_name);
        }
    }
}
