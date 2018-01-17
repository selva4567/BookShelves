package com.example.selvakumarsm.bookshelves.bookslist.models;

import com.example.selvakumarsm.bookshelves.bookslist.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by selvakumar.sm on 1/17/2018.
 */

//Class to retrieve the data either from cache/persistent storage or from service call.
public class DataRepo implements ServiceCalls.ServiceCallsCallback{

    private ServiceCalls serviceCalls;
    private DataRepoCallback callback;
    //TODO: Access Caching technique from here.

    public DataRepo(DataRepoCallback callback){
        this.callback = callback;
        this.serviceCalls = new ServiceCalls(this);
    }

    public void getBooksList(){
        //TODO: check database for any caching and if any, modify return data
        serviceCalls.makeRequest(Constants.BOOKLIST_REQUEST_TAG, Constants.BOOKLIST_RESPONSE_CODE);
    }

    public List<BooksList> parseBooksJsonResponse(JSONObject responseObject) throws JSONException {
        List<BooksList>  booksList = new ArrayList<>();
        int totalBooks = responseObject.getInt("totalItems");
        if(totalBooks != 0){
            JSONArray items = responseObject.getJSONArray("items");
            if(items != null && items.length() > 0){
                for(int i=0; i<items.length(); i++){
                    JSONObject bookItem = items.getJSONObject(i);

                    String title, description, thumbnailUrl;
                    String[] authors;
                    int ratings;

                    JSONObject bookVolumeInfo = bookItem.getJSONObject(Constants.BOOK_VOLUME_INFO_KEY);

                    //book description, book title and ratingsCount
                    description = bookVolumeInfo.getString(Constants.BOOK_DESCRIPTION_KEY);
                    title = bookVolumeInfo.getString(Constants.BOOK_TITLE_KEY);
                    ratings = bookVolumeInfo.getInt(Constants.BOOK_RATINGS_COUNT_KEY);

                    //authors
                    JSONArray authorsJSON = bookVolumeInfo.getJSONArray(Constants.BOOK_AUTHOR_KEY);
                    authors = new String[authorsJSON.length()];
                    for(int j=0; j<authorsJSON.length(); j++){
                        authors[j] = authorsJSON.getString(j);
                    }

                    //small thumbnails
                    JSONObject bookImages = bookVolumeInfo.getJSONObject(Constants.BOOK_IMAGES_KEY);
                    thumbnailUrl = bookImages.getString(Constants.BOOK_SMALL_THUMBNAIL_KEY);

                    booksList.add(new BooksList(title, authors, description, ratings, thumbnailUrl));
                }
            }
        }
        return booksList;
    }

    @Override
    public void onSuccess(JSONObject responseObject, int responseCode) {
        switch (responseCode){
            case Constants.BOOKLIST_RESPONSE_CODE:

                try {
                    List<BooksList> booksList = parseBooksJsonResponse(responseObject);
                    callback.onBooksListReady(booksList);
                }catch (Exception exception){
                    callback.onErrorOccured(exception.getMessage());
                }
                break;
        }
    }

    //Error from Volley - error occured while doing network operations
    @Override
    public void onError(String error, int responseCode) {
        callback.onErrorOccured(error);
    }

    public interface DataRepoCallback {
        void onBooksListReady(List<BooksList> booksLists);
        void onErrorOccured(String errorMessage);
    }
}
