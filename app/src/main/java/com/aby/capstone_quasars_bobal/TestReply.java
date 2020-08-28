package com.aby.capstone_quasars_bobal;

public class TestReply {

    String question;
    String filePath;

    public TestReply(String question, String filePath) {
        this.question = question;
        this.filePath = filePath;
    }

    public String getQuestion() {
        return question;
    }

    public String getFilePath() {
        return filePath;
    }
}
