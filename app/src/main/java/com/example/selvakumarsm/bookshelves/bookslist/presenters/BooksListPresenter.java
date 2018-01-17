package com.example.selvakumarsm.bookshelves.bookslist.presenters;

import android.util.Log;

import com.example.selvakumarsm.bookshelves.bookslist.models.BooksList;
import com.example.selvakumarsm.bookshelves.bookslist.models.DataRepo;

import java.util.List;

/**
 * Created by selvakumar.sm on 1/17/2018.
 */

//Class which drives the UI(activity/fragment)
public class BooksListPresenter implements DataRepo.DataRepoCallback{

    private final String TAG = BooksListPresenter.class.getSimpleName();
    private BooksListPresenterCallback callback;
    private DataRepo repository;

    public BooksListPresenter(BooksListPresenterCallback callback){
        this.callback = callback;
        repository = new DataRepo(this);
    }

    public void getBooksList(){
        callback.showProgressDialog(null);
        repository.getBooksList();
    }

    @Override
    public void onBooksListReady(List<BooksList> booksLists) {
        callback.hideProgressDialog();
        Log.d(TAG, "Success "+booksLists.size());
        callback.displayBooksList(booksLists);
    }

    @Override
    public void onErrorOccured(String errorMessage) {
        callback.hideProgressDialog();
        callback.showError(errorMessage);
        Log.d(TAG, "Error \n"+errorMessage);
    }

    public interface BooksListPresenterCallback {
        void showProgressDialog(String message);
        void hideProgressDialog();
        void displayBooksList(List<BooksList> booksLists);
        void showError(String message);
    }
}
