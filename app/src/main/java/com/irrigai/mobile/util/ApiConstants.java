package com.irrigai.mobile.util;

public class ApiConstants {
    // Update this with your backend URL
    public static final String BASE_URL = "http://10.0.2.2:3000/api/"; // Android emulator
    // For physical device: use your computer's IP, e.g., "http://192.168.1.100:3000/api/"
    
    // Auth Endpoints
    public static final String LOGIN = "auth/login";
    public static final String SIGNUP = "auth/signup";
    
    // Data Endpoints
    public static final String WEATHER = "weather";
    public static final String DASHBOARD = "dashboard";
    public static final String ROBOTS = "robots";
    public static final String WORKERS = "workers";
    public static final String STATS = "stats";
    public static final String TASKS = "tasks";
    
    // Headers
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
}

