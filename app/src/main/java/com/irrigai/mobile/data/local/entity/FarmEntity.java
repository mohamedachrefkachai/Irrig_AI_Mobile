package com.irrigai.mobile.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Farm belongs to one owner (FarmOwner). ownerId = UserEntity.id for userType "owner".
 */
@Entity(tableName = "farms")
public class FarmEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    /** UserEntity.id of the owner */
    public int ownerId;

    public FarmEntity() {}

    public FarmEntity(String name, int ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }
}
