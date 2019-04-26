package com.popular.movies.ortal.layouts;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.popular.movies.ortal.R;
import com.popular.movies.ortal.data.Movie;
import com.squareup.picasso.Picasso;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    private static final String IMAGE_BASE_URL_SMALL = "http://image.tmdb.org/t/p/w185";
    private final Context mContext;
    private final MovieViewHolder.Listener mListener;

    public ImageView mImageView;
    public TextView mTitleView;
    private Movie mItem;
    private int mPosition;

    public MovieViewHolder(View v, Context context, MovieViewHolder.Listener listener) {
        super(v);
        mListener = listener;
        mImageView = v.findViewById(R.id.image);
        mTitleView = v.findViewById(R.id.title);

        mContext = context;
    }

    public void assignItem(final Movie item, final int position) {
        mPosition = position;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMovieClick(item, position);
            }
        });
        mItem = item;
        Picasso.with(mContext)
                .load(IMAGE_BASE_URL_SMALL + mItem.poster_path)
                .fit().centerCrop()

                .into(mImageView);

        mTitleView.setText(mItem.title);
    }

    public interface Listener {
        void onMovieClick(Movie acc, int position);
    }

}