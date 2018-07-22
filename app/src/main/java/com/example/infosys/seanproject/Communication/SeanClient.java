package com.example.infosys.seanproject.Communication;

import com.example.infosys.seanproject.Class.Profile;
import com.example.infosys.seanproject.Util.Constant;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rx.Observable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeanClient {

    private static SeanClient instance;
    private static SeanServices services;

    private SeanClient(){
        final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        services = retrofit.create(SeanServices.class);
    }

    public static SeanClient getInstance(){
        if(instance==null){
            instance = new SeanClient();
        }
        return instance;
    }

    public Observable<Profile> getProfileData(){
        return services.getProfile();
    }
}
