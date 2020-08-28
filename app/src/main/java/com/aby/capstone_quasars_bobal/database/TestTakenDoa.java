package com.aby.capstone_quasars_bobal.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface TestTakenDoa {


    @Query("SELECT * FROM TestTaken")
    Maybe<List<TestTaken>> getAll();

    @Query("SELECT * FROM TestTaken order by created_on desc limit 1")
    Maybe<List<TestTaken>> getLatestTestTaken();


    @Insert
    void insertAll(TestTaken... notes);

    @Update
    void update(TestTaken note);

    @Delete
    void delete(TestTaken note);
}
