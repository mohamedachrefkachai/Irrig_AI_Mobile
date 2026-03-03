package com.irrigai.mobile.api;

import com.irrigai.mobile.model.AuthResponse;
import com.irrigai.mobile.model.Dashboard;
import com.irrigai.mobile.model.Robot;
import com.irrigai.mobile.model.Weather;
import com.irrigai.mobile.model.Worker;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface ApiService {
    // Auth
    @POST("auth/login")
    Call<AuthResponse> login(@Body Map<String, String> credentials);
    
    @POST("auth/signup")
    Call<AuthResponse> signup(@Body Map<String, String> userData);

    // Weather
    @GET("weather")
    Call<Map<String, Object>> getWeather(@Header("Authorization") String token);
    
    // Dashboard
    @GET("dashboard")
    Call<Dashboard> getDashboard(@Header("Authorization") String token);
    
    // Robots
    @GET("robots")
    Call<List<Robot>> getRobots(@Header("Authorization") String token);
    
    // Workers
    @GET("workers")
    Call<List<Worker>> getWorkers(@Header("Authorization") String token);
    
    // Stats
    @GET("stats")
    Call<Map<String, Object>> getStats(@Header("Authorization") String token);
    
    // Tasks
    @GET("tasks")
    Call<List<Map<String, Object>>> getTasks(@Header("Authorization") String token);
    
    @POST("tasks")
    Call<Map<String, Object>> createTask(@Header("Authorization") String token, @Body Map<String, Object> task);
    
    @PATCH("Mobile/tasks/{id}/status")
    Call<Map<String, Object>> updateTaskStatus(
        @Header("Authorization") String token,
        @Path("id") String taskId,
        @Body Map<String, Object> status
    );

    // Invitation & Farm Access
    @POST("invitation/validate")
    Call<Map<String, Object>> validateInvitationCode(@Body Map<String, String> data);

    @GET("farm/{farmId}")
    Call<Map<String, Object>> getFarmData(
        @Path("farmId") String farmId,
        @Header("Authorization") String token
    );

    // Owner Worker Management
    @POST("owner/workers/add-worker")
    Call<Map<String, Object>> addWorker(
        @Header("Authorization") String token,
        @Body Map<String, String> workerData
    );

    @GET("owner/workers/list")
    Call<Map<String, Object>> listWorkers(
        @Header("Authorization") String token,
        @Path("ownerId") String ownerId
    );

    // Worker Info
    @GET("worker/info")
    Call<Map<String, Object>> getWorkerInfo(
        @Header("Authorization") String token
    );
}

