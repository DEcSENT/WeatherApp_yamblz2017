package com.mishkun.weatherapp.db;
/*
 * Created by DV on Space 5 
 * 07.08.2017
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface CacheEntityDAO {

    @Query("SELECT * FROM Cache")
    Flowable<CacheEntity> getCache();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCache(CacheEntity cacheEntity);
}
