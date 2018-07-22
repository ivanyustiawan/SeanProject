package com.example.infosys.seanproject.Communication;

import com.example.infosys.seanproject.Class.Profile;

import retrofit2.http.GET;

public interface SeanServices {

//    rx.Observable <List<GitHubRepo>> getStarredRepositories(@Path("user") String userName);
    @GET("api?m=random")
    rx.Observable <Profile> getProfile();
}
