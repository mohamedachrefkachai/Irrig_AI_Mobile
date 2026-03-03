package com.irrigai.mobile.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @NonNull
    public String email;

    @NonNull
    public String password;

    @NonNull
    public String name;

    @NonNull
    public String userType; // "owner" or "worker"

    public String farmName; // can be null for workers

    public String workerIdentifier; // for workers: e.g. "W001" - used for login

    public UserEntity(@NonNull String email, @NonNull String password, @NonNull String name, @NonNull String userType,
            String farmName, String workerIdentifier) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.userType = userType;
        this.farmName = farmName;
        this.workerIdentifier = workerIdentifier;
    }
}
