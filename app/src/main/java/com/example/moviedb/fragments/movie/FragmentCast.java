package com.example.moviedb.fragments.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.activity.ActivityCastDetails;
import com.example.moviedb.adapters.CastListViewAdapter;
import com.example.moviedb.internet.TestInternetConnection;
import com.example.moviedb.model.movie.Cast;
import com.example.moviedb.model.movie.MovieDetails;
import com.example.moviedb.retrofit.ApiClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCast extends Fragment {

    List<Cast> castsList;
    ListView castListView;
    CastListViewAdapter castListViewAdapter;
    private ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cast_fragment, container, false);

        final int itemId = getActivity().getIntent().getIntExtra("id", 1);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar_cast_fragment);
        castListView = (ListView) rootView.findViewById(R.id.list_view_for_cast);
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_cast_fragment);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!TestInternetConnection.checkConnection(getContext())) {
                    Toast.makeText(getContext(), "no internet connection", Toast.LENGTH_SHORT).show();
                }else {
                    Call<MovieDetails> call = ApiClient.getClient().getGenre(itemId, Const.API_KEY);
                    call.enqueue(new Callback<MovieDetails>() {
                        @Override
                        public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                            castsList = response.body().getCasts().getCast();
                            castListViewAdapter = new CastListViewAdapter(getContext(), castsList);
                            castListView.setAdapter(castListViewAdapter);
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<MovieDetails> call, Throwable t) {

                        }
                    });
                }
                castListView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.destroyDrawingCache();
            }
        });
        castsList = new ArrayList<>();

        castListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ActivityCastDetails.class);
                intent.putExtra("cast_id", castsList.get(position).getId());
                intent.putExtra("profile_path", castsList.get(position).getProfilePath());
                intent.putExtra("name", castsList.get(position).getName());
                startActivity(intent);
            }
        });

        Call<MovieDetails> call = ApiClient.getClient().getGenre(itemId, Const.API_KEY);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                castsList = response.body().getCasts().getCast();
                castListViewAdapter = new CastListViewAdapter(getContext(), castsList);
                castListView.setAdapter(castListViewAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {

            }
        });
        castListView.setVisibility(View.VISIBLE);
        return rootView;
    }

    public static FragmentCast newInstance() {
        return new FragmentCast();
    }
}
