package com.aby.capstone_quasars_bobal.model;

public class Article {
    private String imageUrl;
    private String title;
    private String sourceUrl;

    public Article(String imageUrl, String title, String sourceUrl) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.sourceUrl = sourceUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }
}
