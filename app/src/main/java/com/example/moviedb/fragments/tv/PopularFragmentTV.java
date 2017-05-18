package com.example.moviedb.fragments.tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.adapters.PopularTVShowAdapter;
import com.example.moviedb.internet.TestInternetConnection;
import com.example.moviedb.model.tv.standart.TVResponse;
import com.example.moviedb.model.tv.standart.TVResult;
import com.example.moviedb.retrofit.ApiClient;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularFragmentTV extends Fragment{

    List<TVResult> list;
    RecyclerView rv;
    ProgressBar progressBar;
    PopularTVShowAdapter popularTVShowAdapter;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    Call<TVResponse> call;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_popular_tv, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout_popular_tv);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view_popular_tv);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar_popular_tv);
        linearLayoutManager = new LinearLayoutManager(getContext());
        resp();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!TestInternetConnection.checkConnection(getContext())) {
                    Toast.makeText(getContext(), "no internet connection", Toast.LENGTH_SHORT).show();
                }else {
                    call = ApiClient.getClient().getPopularyTVShow(1, Const.API_KEY);
                    call.enqueue(new Callback<TVResponse>() {
                        @Override
                        public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                            list = response.body().getResults();
                            popularTVShowAdapter = new PopularTVShowAdapter(getContext(), list);
                            rv.setLayoutManager(linearLayoutManager);
                            rv.setAdapter(popularTVShowAdapter);
                        }

                        @Override
                        public void onFailure(Call<TVResponse> call, Throwable t) {

                        }
                    });
                }
                popularTVShowAdapter = new PopularTVShowAdapter(getContext(), list);
                rv.setAdapter(popularTVShowAdapter);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.destroyDrawingCache();
            }
        });
        return rootView;
    }

    public void resp(){
        call = ApiClient.getClient().getPopularyTVShow(1, Const.API_KEY);
        call.enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                list = response.body().getResults();
                popularTVShowAdapter = new PopularTVShowAdapter(getContext(), list);
                rv.setLayoutManager(linearLayoutManager);
                rv.addItemDecoration(new MaterialViewPagerHeaderDecorator());
                rv.setAdapter(popularTVShowAdapter);
                rv.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
            }
        });
    }

    public static PopularFragmentTV newInstance() {
        return new PopularFragmentTV();
    }

}
