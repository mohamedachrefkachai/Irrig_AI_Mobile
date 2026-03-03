package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.irrigai.mobile.data.local.entity.UserEntity;

@Dao
public interface UserDao {

    @Insert
    long insertUser(UserEntity user);

    @Query("UPDATE users SET workerIdentifier = :identifier WHERE id = :userId")
    void updateWorkerIdentifier(int userId, String identifier);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    UserEntity getUserByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    UserEntity getUserById(int userId);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    UserEntity getUserByEmail(String email);

    /** For workers: login with identifier + password */
    @Query("SELECT * FROM users WHERE workerIdentifier = :identifier AND password = :password AND userType = 'worker' LIMIT 1")
    UserEntity getWorkerByIdentifierAndPassword(String identifier, String password);
}
