package com.mukeshkpdeveloper.infoenumtask.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mukeshkpdeveloper.infoenumtask.R;
import com.mukeshkpdeveloper.infoenumtask.adapters.SelectedThingsAdapter;
import com.mukeshkpdeveloper.infoenumtask.models.Things;
import com.mukeshkpdeveloper.infoenumtask.utils.Util;

import java.util.ArrayList;
import java.util.Random;

public class ThingActivity extends AppCompatActivity {
    private String TAG = "ThingActivity";
    private ArrayList<Things> selectedThingsList;
    private RecyclerView mRecyclerView;
    private SelectedThingsAdapter mAdapter;
    private TextView tvThingName;
    private CardView cvBack;
    public static final long DEFAULT_ANIMATION_DURATION = 2500L;
    protected View mRocket;
    protected float mScreenHeight;

    public static Things getRandom(ArrayList<Things> array) {
        int rnd = new Random().nextInt(array.size());
        return array.get(rnd);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing);

        initViews();
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.rv_things);
        tvThingName = findViewById(R.id.tv_thing_name);
        cvBack = findViewById(R.id.cv_back);
        mRocket = findViewById(R.id.rocket);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mScreenHeight = displaymetrics.heightPixels;

        Intent i = getIntent();
        selectedThingsList = (ArrayList<Things>) i.getSerializableExtra("SELECTED_THINGS");
        mAdapter = new SelectedThingsAdapter(selectedThingsList);
        LinearLayoutManager manager = new LinearLayoutManager(ThingActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        tvThingName.setText("waiting...");

        onStartAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSelectedResult();
            }
        },DEFAULT_ANIMATION_DURATION);

        cvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void onStartAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, -mScreenHeight);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mRocket.setTranslationY(value);
            }
        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(DEFAULT_ANIMATION_DURATION);
        valueAnimator.start();
    }

    private void getSelectedResult() {
        CardView cardView = findViewById(R.id.cardView);
        cardView.setVisibility(View.VISIBLE);
        Things things = getRandom(selectedThingsList);
        tvThingName.setText(things.getThingName());

        /*
         * Remove Random selected Thing*/
        selectedThingsList.remove(things);
        mAdapter.notifyDataSetChanged();
    }
}