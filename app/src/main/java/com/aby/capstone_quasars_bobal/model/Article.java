package com.aby.capstone_quasars_bobal.model;

public class Article {
    private String imageUrl;
    private String title;
    private String sourceUrl;
    private String source_name;
    private String author;
    private String publishedat;
    private String highlighting;


    public Article(String imageUrl, String title, String sourceUrl) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.sourceUrl = sourceUrl;
    }

    public Article(String imageUrl, String title, String sourceUrl, String source_name, String author, String publishedat, String highlighting) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.sourceUrl = sourceUrl;
        this.source_name = source_name;
        this.author = author;
        this.publishedat = publishedat;
        this.highlighting = highlighting;
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

    public String getSource_name() {
        return source_name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishedat() {
        return publishedat.split("T")[0];
    }

    public String getHighlighting() {
        return highlighting;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishedat(String publishedat) {
        this.publishedat = publishedat;
    }

    public void setHighlighting(String highlighting) {
        this.highlighting = highlighting;
    }
}
