package com.mukeshkpdeveloper.infoenumtask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mukeshkpdeveloper.infoenumtask.adapters.ThingsAdapter;
import com.mukeshkpdeveloper.infoenumtask.databinding.ActivityMainBinding;
import com.mukeshkpdeveloper.infoenumtask.models.Things;
import com.mukeshkpdeveloper.infoenumtask.networking.RetrofitClient;
import com.mukeshkpdeveloper.infoenumtask.ui.ThingActivity;
import com.mukeshkpdeveloper.infoenumtask.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefreshLayout;
    private String TAG = "MainActivity";
    private ArrayList<Things> mModelList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ThingsAdapter mAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initViews();
    }

    private void initViews() {
        geThingsData();
        mAdapter = new ThingsAdapter(mModelList);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        binding.rvThings.setHasFixedSize(true);
        binding.rvThings.setLayoutManager(manager);
        binding.rvThings.setAdapter(mAdapter);

        // SetOnRefreshListener on SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshLayout.setRefreshing(false);
                geThingsData();
            }
        });

        binding.cvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Things> selectedThingsList = new ArrayList<>();
                for (int i = 0; i < mModelList.size(); i++) {
                    Log.e(TAG, "Mukesh onClick: " + mModelList.get(i).getThingName() + " || " + mModelList.get(i).isSelected());
                    if (mModelList.get(i).isSelected()) {
                        selectedThingsList.add(new Things(mModelList.get(i).getThingName(), mModelList.get(i).isSelected()));
                    }
                }
                Log.e(TAG, "Mukesh Selected List: " + selectedThingsList.toString());
                if (selectedThingsList.size() <= 2) {
                    Toast.makeText(MainActivity.this, "Please Select atLeast 3 Things or more", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(MainActivity.this, ThingActivity.class);
                    i.putExtra("SELECTED_THINGS", selectedThingsList);
                    startActivity(i);
                }
            }
        });
    }

    /*
     * create thing lists*/
    @SuppressLint("LongLogTag")
    private void geThingsData() {
        Util.showProgressBar(MainActivity.this, true);
        Call<JsonObject> call = RetrofitClient.getInstance().getApiInterface().users("application/x-www-form-urlencoded");
        call.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Util.showProgressBar(MainActivity.this, false);
                if (response.code() == 200) {
                    try {
                        mModelList.clear();
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            mModelList.add(new Things(obj.getString("first_name") + " " + obj.getString("last_name"), false));
                        }
                        mAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Util.showShortToast(MainActivity.this, "" + jObjError);
                    } catch (Exception e) {
                        Util.showShortToast(MainActivity.this, "Server Error");
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Util.showShortToast(MainActivity.this, t.getMessage());
                Util.log(t.getMessage());
            }
        });
    }
}