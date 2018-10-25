package com.example.restclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.restclient.TrackApi.TrackApiService;
import com.example.restclient.models.Track;
import com.example.restclient.models.TrackAnswer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView txt1;

    private Retrofit retrofit;

    private static final String TAG ="MyService: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = (TextView)findViewById(R.id.txt1);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://147.83.7.155:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getData();
    }

    private void getData() {
        TrackApiService service = retrofit.create(TrackApiService.class);
        Call<TrackAnswer> trackAnswerCall = service.getTrackList();

        trackAnswerCall.enqueue(new Callback<TrackAnswer>() {
            @Override
            public void onResponse(Call<TrackAnswer> call, Response<TrackAnswer> response) {
                if (response.isSuccessful()) {
                    TrackAnswer trackAnswer = response.body();
                    ArrayList<Track> trackList = trackAnswer.getResults();

                    Track t = trackList.get(0);
                    txt1.setText(t.getSinger());

                    /*for (int i = 0; i < trackList.size(); i++) {
                        Track t = trackList.get(i);
                        Log.i(TAG, " Track: " + t.getSinger());
                    }*/
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<TrackAnswer> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }
}
