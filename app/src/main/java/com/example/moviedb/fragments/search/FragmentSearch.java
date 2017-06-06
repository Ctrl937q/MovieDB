package com.example.moviedb.fragments.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.adapters.AdapterForSearch;
import com.example.moviedb.internet.TestInternetConnection;
import com.example.moviedb.model.search.Result;
import com.example.moviedb.model.search.SearchResponse;
import com.example.moviedb.retrofit.ApiClient;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSearch extends Fragment {

    List<Result> list;
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    Call<SearchResponse> call;
    String textSearch;
    AdapterForSearch adapterForSearch;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_search, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_search);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar_search);
        linearLayoutManager = new LinearLayoutManager(getContext());
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout_search);
        textSearch = getActivity().getIntent().getStringExtra("text");
        setHasOptionsMenu(true);
        call = ApiClient.getClient().getSearch(1, textSearch, Const.API_KEY);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.body().getTotalResults() == 0) {
                    Toast.makeText(getActivity(), "no results found", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    list = response.body().getResults();
                    adapterForSearch = new AdapterForSearch(getActivity(), list, textSearch);
                    rv.setLayoutManager(linearLayoutManager);
                    rv.addItemDecoration(new MaterialViewPagerHeaderDecorator());
                    rv.setAdapter(adapterForSearch);
                    rv.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.d("text", " " + response.body().getTotalResults());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!TestInternetConnection.checkConnection(getContext())) {
                    Toast.makeText(getContext(), "no internet connection", Toast.LENGTH_SHORT).show();
                } else {
                    call = ApiClient.getClient().getSearch(1, textSearch, Const.API_KEY);
                    call.enqueue(new Callback<SearchResponse>() {
                        @Override
                        public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                            list = response.body().getResults();
                            adapterForSearch = new AdapterForSearch(getActivity(), list, textSearch);
                            rv.setLayoutManager(linearLayoutManager);
                            rv.setAdapter(adapterForSearch);
                            rv.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(Call<SearchResponse> call, Throwable t) {

                        }
                    });
                }
                adapterForSearch = new AdapterForSearch(getActivity(), list, textSearch);
                rv.setAdapter(adapterForSearch);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.destroyDrawingCache();
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                rv.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                call = ApiClient.getClient().getSearch(1, query, Const.API_KEY);
                call.enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (!TestInternetConnection.checkConnection(getActivity())) {
                           /* viewPager.setVisibility(View.GONE);
                            button.setVisibility(View.VISIBLE);
                            textViewRetry.setVisibility(View.VISIBLE);*/
                        }

                        if (response.body().getTotalResults() == 0) {
                            Toast.makeText(getActivity(), "no results found", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        } else {
                            list = response.body().getResults();
                            adapterForSearch = new AdapterForSearch(getActivity(), list, textSearch);
                            rv.setLayoutManager(linearLayoutManager);
                            rv.setAdapter(adapterForSearch);
                            rv.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static FragmentSearch newInstance() {
        return new FragmentSearch();
    }
}
