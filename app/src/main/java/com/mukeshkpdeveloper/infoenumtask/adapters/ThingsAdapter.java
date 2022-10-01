package com.mukeshkpdeveloper.infoenumtask.adapters;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mukeshkpdeveloper.infoenumtask.R;
import com.mukeshkpdeveloper.infoenumtask.models.Things;

import java.util.ArrayList;

public class ThingsAdapter extends RecyclerView.Adapter<ThingsAdapter.MyViewHolder> {

    private ArrayList<Things> mThingsList;
    long DURATION = 200;
    private boolean on_attach = true;

    public ThingsAdapter(ArrayList<Things> modelList) {
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
        holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
        holder.ivChecked.setVisibility(View.GONE);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setSelected(!model.isSelected());
              //  holder.view.setBackgroundColor(model.isSelected() ? Color.CYAN : Color.WHITE);
                holder.ivChecked.setVisibility(model.isSelected() ? View.VISIBLE : View.GONE);
            }
        });
        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View itemView, int i) {
        if(!on_attach){
            i = -1;
        }
        boolean not_first_item = i == -1;
        i = i + 1;
        itemView.setTranslationX(itemView.getX() + 400);
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(itemView, "translationX", itemView.getX() + 400, 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(itemView, "alpha", 1.f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animatorTranslateY.setStartDelay(not_first_item ? DURATION : (i * DURATION));
        animatorTranslateY.setDuration((not_first_item ? 2 : 1) * DURATION);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();
    }

    @Override
    public int getItemCount() {
        return mThingsList == null ? 0 : mThingsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView textView;
        private ImageView ivChecked;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textView = itemView.findViewById(R.id.tv_thing_name);
            ivChecked = itemView.findViewById(R.id.iv_checked);
        }
    }
}
