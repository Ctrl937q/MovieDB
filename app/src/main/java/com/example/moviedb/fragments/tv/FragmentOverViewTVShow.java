package com.example.moviedb.fragments.tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.model.tv.details.TVDetailsResponseTV;
import com.example.moviedb.retrofit.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOverViewTVShow extends Fragment{

    TextView textViewOverView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_overview_tv_show, container, false);
        textViewOverView = (TextView) rootView.findViewById(R.id.text_view_overView_tv);
        final int itemId = getActivity().getIntent().getIntExtra("id", 1);
        Log.d("id", " " + itemId);
        Call<TVDetailsResponseTV> call = ApiClient.getClient().getTVDetails(itemId, Const.API_KEY);
        call.enqueue(new Callback<TVDetailsResponseTV>() {
            @Override
            public void onResponse(Call<TVDetailsResponseTV> call, Response<TVDetailsResponseTV> response) {
                String overView = response.body().getOverview();
                if(overView.equals("")){
                    textViewOverView.setText("No OverView");
                }else {
                    textViewOverView.setText(overView);
                }
            }

            @Override
            public void onFailure(Call<TVDetailsResponseTV> call, Throwable t) {

            }
        });
        return rootView;
    }
}
