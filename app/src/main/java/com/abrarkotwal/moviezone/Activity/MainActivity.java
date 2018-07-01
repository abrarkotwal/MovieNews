package com.abrarkotwal.moviezone.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.abrarkotwal.moviezone.Adapter.MoviesAdapter;
import com.abrarkotwal.moviezone.Adapter.Pojo.Movie;
import com.abrarkotwal.moviezone.DB.Api;
import com.abrarkotwal.moviezone.DB.ApiClient;
import com.abrarkotwal.moviezone.R;
import com.abrarkotwal.moviezone.SessionManager.AlertDialogManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.abrarkotwal.moviezone.DB.ApiClient.BASE_URL;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        recyclerView=  findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        refreshLayout=  findViewById(R.id.refresh);
        refreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                initView();
                Toast.makeText(MainActivity.this, "Movies Refreshed", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });

        fetchData();
    }

    private void fetchData() {
        ApiClient apiClient = new ApiClient();
        Api api =apiClient.getClient().create(Api.class);

        Call<Movie> call = api.getUpcommingMovieList(Api.API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                int statusCode = response.code();
                if (statusCode == 400){
                    Toast.makeText(MainActivity.this,"Api not found",Toast.LENGTH_SHORT).show();
                }else {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(), movies));
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable error) {
                DialogInterface.OnClickListener onClickTryAgain = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fetchData();
                    }
                };

                if (error instanceof IOException){
                    AlertDialogManager.internetConnectionErrorAlert(MainActivity.this, onClickTryAgain);
                }

            }
        });
    }


    public void callInfo(View view){
        Intent intent = new Intent(this,InfoActivity.class);
        startActivity(intent);
    }
}
