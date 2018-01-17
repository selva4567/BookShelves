package com.example.selvakumarsm.bookshelves.bookslist.adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.selvakumarsm.bookshelves.R;
import com.example.selvakumarsm.bookshelves.bookslist.BookShelvesApplication;
import com.example.selvakumarsm.bookshelves.bookslist.models.BooksList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by selvakumar.sm on 1/17/2018.
 */

public class BooksListAdapter extends RecyclerView.Adapter<BooksListAdapter.BooksListHolder> {

    private final String TAG = BooksListAdapter.class.getSimpleName();
    private List<BooksList> booksList;
    private BooksListAdapterCallback callback;

    public BooksListAdapter(BooksListAdapterCallback callback, List<BooksList> booksList){
        this.booksList = booksList;
        this.callback = callback;
    }
    @Override
    public BooksListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        return new BooksListHolder(view);
    }

    @Override
    public void onBindViewHolder(final BooksListHolder holder, final int position) {
        BooksList books = booksList.get(position);
        holder.bookTitle.setText(books.getTitle());
        holder.bookDescriptions.setText(books.getDescription());
        holder.bookAuthors.setText(setAuthors(books.getAuthor()));

        //ratings settings
        holder.bookRating.setImageDrawable(getRatingDrawable(books.getRating()));
        holder.bookRatingsCount.setText(books.getRating()+"");

        //thumbnail settings
        holder.bookSmallThumbnail.setDefaultImageResId(R.mipmap.default_image);
        holder.bookSmallThumbnail.setErrorImageResId(R.mipmap.load_error);
        holder.bookSmallThumbnail.setImageUrl(books.getThumbnailId(), BookShelvesApplication.getVolleyImageLoader());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onBookListItemClicked(holder.bookTitle.getText().toString(), holder.bookAuthors.getText().toString(), holder.bookDescriptions.getText().toString(), holder.bookRatingsCount.getText().toString());
            }
        });
    }

    private Drawable getRatingDrawable(int ratingCount){
        if(ratingCount < 5){
            return BookShelvesApplication.getAppContext().getDrawable(R.mipmap.low_rating);
        }else if(ratingCount >= 5 && ratingCount <10){
            return BookShelvesApplication.getAppContext().getDrawable(R.mipmap.medium_rating);
        }else {
            return BookShelvesApplication.getAppContext().getDrawable(R.mipmap.high_rating);
        }
    }

    private String setAuthors(String[] authors){

        String author = "";
        for(int i=0; i<authors.length; i++){
            author += authors[i]+", ";
        }
        return author.substring(0, author.length()-2);
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class BooksListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ratingCountText)
        TextView bookRatingsCount;
        @BindView(R.id.bookTitle)
        TextView bookTitle;
        @BindView(R.id.bookAuthors)
        TextView bookAuthors;
        @BindView(R.id.bookDescription)
        TextView bookDescriptions;
        @BindView(R.id.thumbnail)
        NetworkImageView bookSmallThumbnail;
        @BindView(R.id.ratingImage)
        ImageView bookRating;

        View itemView;
        public BooksListHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public interface BooksListAdapterCallback {
        void onBookListItemClicked(String bookTitle, String bookAuthors, String bookDescription, String bookRatingCount);
    }
}
