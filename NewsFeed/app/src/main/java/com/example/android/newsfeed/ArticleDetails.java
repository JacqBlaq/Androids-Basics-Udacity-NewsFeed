package com.example.android.newsfeed;

/**
 * Created by Jacquelyn Gboyor on 11/13/2017.
 */

public class ArticleDetails {

    private String title;
    private String sectionName;
    private String rating;
    private String picture;
    private String author;
    private String website;
    private String date;

    public ArticleDetails(String tit, String name, String rate, String pic, String writer, String web, String dates){
        title = tit;
        sectionName = name;
        rating = rate;
        picture = pic;
        author = writer;
        website = web;
        date = dates;
    }

    public String getTitle() {
        return title;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getRating() {
        return rating;
    }

    public String getPicture() {
        return picture;
    }

    public String getAuthor() {
        return author;
    }

    public String getWebsite() {
        return website;
    }

    public String getDate() {
        return date;
    }
}//End
