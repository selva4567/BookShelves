package com.example.selvakumarsm.bookshelves.bookslist.views;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.selvakumarsm.bookshelves.R;
import com.example.selvakumarsm.bookshelves.bookslist.Constants;
import com.example.selvakumarsm.bookshelves.bookslist.adapters.BooksListAdapter;
import com.example.selvakumarsm.bookshelves.bookslist.models.BooksList;
import com.example.selvakumarsm.bookshelves.bookslist.presenters.BooksListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements BooksListPresenter.BooksListPresenterCallback, BooksListAdapter.BooksListAdapterCallback{

    private final String TAG = MainActivity.class.getSimpleName();
    private final String BOOKS_LIST = "BOOKS_LIST";
    private final String BOTTOMSHEET_STATE = "BOTTOMSHEET_STATE";
    private final String BOTTOMSHEET_TITLE = "BOTTOMSHEET_TITLE";
    private final String BOTTOMSHEET_AUTHORS = "BOTTOMSHEET_AUTHORS";
    private final String BOTTOMSHEET_DESCRIPTION = "BOTTOMSHEET_DESCRIPTION";
    private final String BOTTOMSHEET_RATINGCOUNT = "BOTTOMSHEET_RATINGCOUNT";
    private int bootmSheetState;
    private Unbinder butterKnifeBinder;
    private BooksListAdapter booksListAdapter;
    private BooksListPresenter presenter;
    private ProgressDialog progressDialog;
    private List<BooksList> booksLists;
    private BottomSheetBehavior bookDetailsSheet;

    @BindView(R.id.bookList)
    RecyclerView booksList;
    @BindView(R.id.exBookTitleValue)
    TextView exBookTitle;
    @BindView(R.id.exBookAuthorValue)
    TextView exBookAuthor;
    @BindView(R.id.exBookDescriptionValue)
    TextView exBookDescription;
    @BindView(R.id.exRatingsCountValue)
    TextView exRatingCount;
    @BindView(R.id.bottom_sheet)
    NestedScrollView bookDetailsBottomSheet;
    @BindView(R.id.rootLayout)
    CoordinatorLayout rootLayout;
    @BindView(R.id.emptyShelfError)
    TextView emptyShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butterKnifeBinder = ButterKnife.bind(this);

        presenter = new BooksListPresenter(this);
        progressDialog = new ProgressDialog(this);
        bookDetailsSheet = BottomSheetBehavior.from(bookDetailsBottomSheet);
        if(savedInstanceState != null){
            booksLists = savedInstanceState.getParcelableArrayList(BOOKS_LIST);
            bootmSheetState = savedInstanceState.getInt(BOTTOMSHEET_STATE);
            displayBooksList(booksLists);
            if(bootmSheetState == BottomSheetBehavior.STATE_EXPANDED)
                onBookListItemClicked(savedInstanceState.getString(BOTTOMSHEET_TITLE), savedInstanceState.getString(BOTTOMSHEET_AUTHORS), savedInstanceState.getString(BOTTOMSHEET_DESCRIPTION), savedInstanceState.getString(BOTTOMSHEET_RATINGCOUNT));
        }else {
            presenter.getBooksList();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        butterKnifeBinder.unbind();
    }

    @Override
    public void showProgressDialog(String message){
        if(progressDialog == null)
            return;
        if(progressDialog.isShowing())
            return;
        if(message == null)
            message = Constants.LOADING_PLACEHOLDER;
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
        lockScreenOrientation();
    }

    @Override
    public void hideProgressDialog(){
        if(progressDialog == null)
            return;

        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
            releaseScreenOrientation();
        }
    }

    @Override
    public void displayBooksList(List<BooksList> booksLists) {
        this.booksLists = booksLists;
        if(booksLists == null || (booksLists != null && booksLists.size() == 0)){
            emptyShelf.setVisibility(View.VISIBLE);
        }else {
            emptyShelf.setVisibility(View.GONE);
            booksListAdapter = new BooksListAdapter(this, booksLists);
            booksList.setAdapter(booksListAdapter);
            booksList.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void showError(String message) {
        final Snackbar snackbar = Snackbar.make(rootLayout, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("CLOSE", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        }).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(BOOKS_LIST, (ArrayList<BooksList>)booksLists);
        outState.putInt(BOTTOMSHEET_STATE, bookDetailsSheet.getState());
        outState.putString(BOTTOMSHEET_TITLE, exBookTitle.getText().toString());
        outState.putString(BOTTOMSHEET_AUTHORS, exBookAuthor.getText().toString());
        outState.putString(BOTTOMSHEET_DESCRIPTION, exBookDescription.getText().toString());
        outState.putString(BOTTOMSHEET_RATINGCOUNT, exRatingCount.getText().toString());
        super.onSaveInstanceState(outState);
    }

    public void lockScreenOrientation(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }

    public void releaseScreenOrientation(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }


    @Override
    public void onBookListItemClicked(String bookTitle, String bookAuthors, String bookDescription, String bookRatingCount) {
        Log.d(TAG, "item clicked "+bookTitle);
        exBookTitle.setText(bookTitle);
        exBookAuthor.setText(bookAuthors);
        exBookDescription.setText(bookDescription);
        exRatingCount.setText(bookRatingCount);
        bookDetailsSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onBackPressed() {
        if(bookDetailsSheet.getState() == BottomSheetBehavior.STATE_EXPANDED){
            bookDetailsSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }else
            super.onBackPressed();
    }
}
