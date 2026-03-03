package com.irrigai.mobile.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.irrigai.mobile.data.local.entity.TreeEntity;

import java.util.List;

@Dao
public interface TreeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TreeEntity tree);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TreeEntity> trees);

    @Query("SELECT * FROM farm_trees WHERE farmId = :farmId")
    List<TreeEntity> getTreesByFarmId(long farmId);

    @Query("DELETE FROM farm_trees WHERE farmId = :farmId")
    void deleteByFarmId(long farmId);

    @Query("SELECT * FROM farm_trees")
    List<TreeEntity> getAllTrees();

    @Query("DELETE FROM farm_trees")
    void deleteAll();

    @Update
    void update(TreeEntity tree);

    @Delete
    void delete(TreeEntity tree);

    @Query("SELECT COUNT(*) FROM farm_trees")
    int getCount();
}
