package com.example.restclient.TrackApi;

import com.example.restclient.models.TrackAnswer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TrackApiService {

    @GET("tracks")
    Call<TrackAnswer> getTrackList();
}
