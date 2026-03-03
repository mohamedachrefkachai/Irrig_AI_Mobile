package com.irrigai.mobile.util;

import android.content.Context;
import android.content.SharedPreferences;

public class StorageHelper {
    private static final String PREFS_NAME = "irrigai_prefs";
    private static final String KEY_TOKEN = "auth_token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_FARM_ID = "farm_id";
    private static final String KEY_FARM_INVITE_CODE = "farm_invite_code";

    private SharedPreferences prefs;
    
    public StorageHelper(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    
    public void saveToken(String token) {
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }
    
    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }
    
    public void removeToken() {
        prefs.edit().remove(KEY_TOKEN).apply();
    }

    public void saveUserId(String userId) {
        prefs.edit().putString(KEY_USER_ID, userId).apply();
    }
    
    public String getUserId() {
        return prefs.getString(KEY_USER_ID, null);
    }
    
    public void saveUserName(String name) {
        prefs.edit().putString(KEY_USER_NAME, name).apply();
    }
    
    public String getUserName() {
        return prefs.getString(KEY_USER_NAME, null);
    }
    
    public void saveUserEmail(String email) {
        prefs.edit().putString(KEY_USER_EMAIL, email).apply();
    }
    
    public String getUserEmail() {
        return prefs.getString(KEY_USER_EMAIL, null);
    }

    public void saveFarmId(String farmId) {
        prefs.edit().putString(KEY_FARM_ID, farmId).apply();
    }

    public String getFarmId() {
        return prefs.getString(KEY_FARM_ID, null);
    }

    public void saveFarmInviteCode(String code) {
        prefs.edit().putString(KEY_FARM_INVITE_CODE, code).apply();
    }

    public String getFarmInviteCode() {
        return prefs.getString(KEY_FARM_INVITE_CODE, null);
    }

    public void clearAll() {
        prefs.edit().clear().apply();
    }
    
    public boolean isAuthenticated() {
        return getToken() != null;
    }

    // Legacy owner methods (kept for backward compatibility)
    public void saveUserType(String userType) {
        prefs.edit().putString("user_type", userType).apply();
    }

    public String getUserType() {
        return prefs.getString("user_type", "worker");
    }

    public void saveUserRole(String role) {
        prefs.edit().putString("user_role", role).apply();
    }

    public String getUserRole() {
        return prefs.getString("user_role", "WORKER");
    }

    public void saveFarmName(String farmName) {
        prefs.edit().putString("farm_name", farmName).apply();
    }

    public String getFarmName() {
        return prefs.getString("farm_name", null);
    }

    public void saveWorkerId(String workerId) {
        prefs.edit().putString("worker_id", workerId).apply();
    }

    public String getWorkerId() {
        return prefs.getString("worker_id", null);
    }
}

