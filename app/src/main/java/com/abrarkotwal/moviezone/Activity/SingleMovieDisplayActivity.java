package com.abrarkotwal.moviezone.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abrarkotwal.moviezone.Adapter.MoviesAdapter;
import com.abrarkotwal.moviezone.Adapter.Pojo.Movie;
import com.abrarkotwal.moviezone.Adapter.Pojo.MovieImages;
import com.abrarkotwal.moviezone.Adapter.Pojo.SingleMovie;
import com.abrarkotwal.moviezone.Adapter.ProductImagesAdapter;
import com.abrarkotwal.moviezone.DB.Api;
import com.abrarkotwal.moviezone.DB.ApiClient;
import com.abrarkotwal.moviezone.R;
import com.abrarkotwal.moviezone.SessionManager.AlertDialogManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.abrarkotwal.moviezone.DB.ApiClient.BASE_URL;

public class SingleMovieDisplayActivity extends AppCompatActivity {
    private ViewPager mPager;
    private CircleIndicator indicator;
    private TextView singleToolTitle,singleTitle,singleOverview;
    private RatingBar singleRating;
    private int movieId;
    private static int currentPage = 0;
    private ImageView cancelActivity;
    private RelativeLayout mainLayout;
    private Api api;
    private ApiClient apiClient;
    private Call<SingleMovie> call1;
    private Call<MovieImages> call2;
    private SingleMovieDisplayActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie_display);

        activity = this;

        movieId = getIntent().getIntExtra("id",0);

        initView();

    }

    private void initView() {
        singleToolTitle =  findViewById(R.id.singleToolTitle);
        singleTitle     =  findViewById(R.id.singleTitle);
        singleOverview  =  findViewById(R.id.singleOverview);

        singleRating    =  findViewById(R.id.singleRating);

        cancelActivity  =  findViewById(R.id.cancelActivity);

        mPager          =  findViewById(R.id.slider);
        indicator       =  findViewById(R.id.indicator);
        mainLayout      =  findViewById(R.id.mainLayout);

        cancelActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        apiClient = new ApiClient();
        api =apiClient.getClient().create(Api.class);

        call1 = api.getMovieDetail(movieId,Api.API_KEY);
        call2 = api.getMovieImageList(movieId,Api.API_KEY);


        fetchMovieInfo();
    }

    private void fetchMovieInfo() {
        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        call1.enqueue(new Callback<SingleMovie>() {
            @Override
            public void onResponse(Call<SingleMovie> call, Response<SingleMovie> response) {
                progressDialog.dismiss();
                if (response.code() == 400){
                    mainLayout.setVisibility(View.GONE);
                    Toast.makeText(activity,"Api not found",Toast.LENGTH_SHORT).show();
                }else {
                    mainLayout.setVisibility(View.VISIBLE);
                    singleTitle.setText(response.body().getTitle());
                    singleToolTitle.setText(response.body().getTitle());
                    singleOverview.setText(response.body().getOverview());
                    singleRating.setRating(Float.parseFloat(String.valueOf(response.body().getPopularity())));
                }
            }

            @Override
            public void onFailure(Call<SingleMovie> call, Throwable error) {
                if (error instanceof IOException){
                    AlertDialogManager.networkError(activity);
                }
            }
        });

        call2.enqueue(new Callback<MovieImages>() {
            @Override
            public void onResponse(Call<MovieImages> call, Response<MovieImages> response) {
                progressDialog.dismiss();
                if (response.code() == 400) {
                    mainLayout.setVisibility(View.GONE);
                    Toast.makeText(activity,"Api not found",Toast.LENGTH_SHORT).show();
                } else {
                    mainLayout.setVisibility(View.VISIBLE);
                    List<MovieImages> movieImages = response.body().getResults();

                    final List<MovieImages> displayImage = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        displayImage.add(movieImages.get(i));
                    }

                    mPager.setAdapter(new ProductImagesAdapter(activity, displayImage));
                    indicator.setViewPager(mPager);

                    if (displayImage.size() != 0) {
                        // Auto start of viewpager
                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            public void run() {
                                if (currentPage == displayImage.size()) {
                                    currentPage = 0;
                                }
                                mPager.setCurrentItem(currentPage++, true);
                            }
                        };
                        Timer swipeTimer = new Timer();
                        swipeTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, 2500, 2500);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieImages> call, Throwable error) {
                if (error instanceof IOException){
                    AlertDialogManager.networkError(activity);
                }
            }
        });

    }
}
