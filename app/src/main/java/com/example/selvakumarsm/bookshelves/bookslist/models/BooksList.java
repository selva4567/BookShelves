package com.example.selvakumarsm.bookshelves.bookslist.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by selvakumar.sm on 1/17/2018.
 */

public class BooksList implements Parcelable{

    private String title;
    private String[] author;
    private String description;
    private int rating;
    private String thumbnailId;

    public BooksList(){}

    public BooksList(String title, String[] author, String description, int rating, String thumbnailId){
        this.title = title;
        this.author = author;
        this.description = description;
        this.rating = rating;
        this.thumbnailId = thumbnailId;
    }

    protected BooksList(Parcel in) {
        title = in.readString();
        author = in.createStringArray();
        description = in.readString();
        rating = in.readInt();
        thumbnailId = in.readString();
    }

    public static final Creator<BooksList> CREATOR = new Creator<BooksList>() {
        @Override
        public BooksList createFromParcel(Parcel in) {
            return new BooksList(in);
        }

        @Override
        public BooksList[] newArray(int size) {
            return new BooksList[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getAuthor() {
        return author;
    }

    public void setAuthor(String[] author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getThumbnailId() {
        return thumbnailId;
    }

    public void setThumbnailId(String thumbnailId) {
        this.thumbnailId = thumbnailId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeStringArray(author);
        dest.writeString(description);
        dest.writeInt(rating);
        dest.writeString(thumbnailId);
    }
}
