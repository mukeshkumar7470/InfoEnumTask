package com.mukeshkpdeveloper.infoenumtask.networking;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


public interface ApiInterface {

    @GET("users")
    Call<JsonObject> users(@Header("Accept") String Accept);
}