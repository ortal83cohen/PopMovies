package com.popular.movies.ortal.layouts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.popular.movies.ortal.R;
import com.popular.movies.ortal.data.Movie;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment {
    private static final String EXTRA_DATA = "extra_data";
    private static final String IMAGE_BASE_URL_SMALL = "http://image.tmdb.org/t/p/w185";

    public ImageView mImageView;
    public TextView mTitle;
    public TextView mText1;
    public TextView mText2;
    public TextView mText3;
    public TextView mText4;
    public TextView mOverview;
    private Movie mMovie;

    public static DetailsFragment newInstance(Movie movie) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_DATA, movie);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        mImageView = view.findViewById(R.id.image);
        mTitle = view.findViewById(R.id.title);
        mText1 = view.findViewById(R.id.text1);
        mText2 = view.findViewById(R.id.text2);
        mOverview = view.findViewById(R.id.overview);

        Bundle arguments = getArguments();
        if (arguments != null) {
            mMovie = arguments.getParcelable(EXTRA_DATA);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mMovie != null) {
            Picasso.with(getActivity())
                    .load(IMAGE_BASE_URL_SMALL + mMovie.poster_path)
                    .fit().centerCrop()

                    .into(mImageView);
            mTitle.setText(mMovie.title);
            mText1.setText(mMovie.release_date);
            mText2.setText(mMovie.vote_average + "/10");
            mOverview.setText(mMovie.overview);
        }
    }
}
