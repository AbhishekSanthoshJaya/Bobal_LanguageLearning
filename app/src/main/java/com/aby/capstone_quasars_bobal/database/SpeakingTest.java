package com.aby.capstone_quasars_bobal.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "SpeakingTest")
public class SpeakingTest  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    private ArrayList<String> questions;

    public SpeakingTest(){

    }

    public SpeakingTest(int id, String name, ArrayList<String> questions) {
        this.id = id;
        this.name = name;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }
}
