package com.aby.capstone_quasars_bobal.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "TestTaken")
public class TestTaken  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String testName;

    @ColumnInfo(name = "created_on")
    private Date createdOn;

    private ArrayList<String> audioPaths;

    private ArrayList<String> questions;

    public TestTaken(){

    }

    public TestTaken(int id, String testName, Date createdOn, ArrayList<String> audioPaths, ArrayList<String> questions) {
        this.id = id;
        this.testName = testName;
        this.createdOn = createdOn;
        this.audioPaths = audioPaths;
        this.questions = questions;
    }

    public TestTaken(String testName, Date createdOn, ArrayList<String> audioPaths, ArrayList<String> questions) {
        this.testName = testName;
        this.createdOn = createdOn;
        this.audioPaths = audioPaths;
        this.questions = questions;
    }

    public TestTaken(String testName,  ArrayList<String> audioPaths, ArrayList<String> questions) {
        this.testName = testName;
        this.createdOn =new Date();
        this.audioPaths = audioPaths;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public String getTestName() {
        return testName;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public ArrayList<String> getAudioPaths() {
        return audioPaths;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public void setAudioPaths(ArrayList<String> audioPaths) {
        this.audioPaths = audioPaths;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }
}