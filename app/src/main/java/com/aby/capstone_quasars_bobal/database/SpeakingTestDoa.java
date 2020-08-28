package com.aby.capstone_quasars_bobal.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface SpeakingTestDoa {

    @Query("SELECT * FROM SpeakingTest")
    Maybe<List<SpeakingTest>> getAll();

    @Insert
    void insertAll(SpeakingTest... notes);

    @Update
    void update(SpeakingTest note);

    @Delete
    void delete(SpeakingTest note);
}
