package com.example.android.newsapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "news_item")
public class NewsItem {
    @PrimaryKey(autoGenerate = true)
    private int primary_key;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "date")
    private String publishedAt;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "image")
    private String urlToImage;

    @Ignore
    public NewsItem( String title, String description, String url, String publishedAt, String urlToImage) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.publishedAt = publishedAt;
        this.urlToImage = urlToImage;
    }

    public NewsItem(int primary_key, String title, String description, String url, String publishedAt, String urlToImage) {
        this.primary_key = primary_key+1;
        this.title = title;
        this.description = description;
        this.url = url;
        this.publishedAt = publishedAt;
        this.urlToImage = urlToImage;
    }
    //    public NewsItem(int primary_key, String title, String description, String url, String publishedAt) {
//        this.primary_key = primary_key+1;
//        this.title = title;
//        this.description = description;
//        this.url = url;
//        this.publishedAt = publishedAt;
//    }
//    public NewsItem(String author, String title, String description, String url, String urlToImage, String publishedAt) {
//        this.author = author;
//        this.title = title;
//        this.description = description;
//        this.url = url;
//        this.urlToImage = urlToImage;
//        this.publishedAt = publishedAt;
//    }

    public int getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(int primary_key) {
        this.primary_key = primary_key;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
