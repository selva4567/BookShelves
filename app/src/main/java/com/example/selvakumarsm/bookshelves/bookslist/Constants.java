package com.example.selvakumarsm.bookshelves.bookslist;

/**
 * Created by selvakumar.sm on 1/17/2018.
 */

public class Constants {

    public final static String BOOKSlIST_END_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:0747532699";
    public final static String LOADING_PLACEHOLDER = "Loading...";
    public final static String BOOKLIST_REQUEST_TAG = "BOOKLIST_REQUEST_TAG";
    public final static int BOOKLIST_RESPONSE_CODE = 101;

    public final static String BOOK_DESCRIPTION_KEY = "description";
    public final static String BOOK_TITLE_KEY = "title";
    public final static String BOOK_AUTHOR_KEY = "authors";
    public final static String BOOK_SMALL_THUMBNAIL_KEY = "smallThumbnail";
    public final static String BOOK_IMAGES_KEY = "imageLinks";
    public final static String BOOK_RATINGS_COUNT_KEY = "ratingsCount";
    public final static String BOOK_VOLUME_INFO_KEY = "volumeInfo";

    public final static String TIMEOUTERROR = "Request Timed out. Please try again later.";
    public final static String NOCONNECTIONERROR = "No Internet Connectivity.";
    public final static String OTHERERRORS = "Unknown error occurred. Please try again later";
}
