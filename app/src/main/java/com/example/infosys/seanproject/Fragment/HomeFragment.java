package com.example.infosys.seanproject.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infosys.seanproject.Application.MobileApplication;
import com.example.infosys.seanproject.Class.Profile;
import com.example.infosys.seanproject.Communication.SeanClient;
import com.example.infosys.seanproject.Listener.BaseListener;
import com.example.infosys.seanproject.Listener.GetProfileListener;
import com.example.infosys.seanproject.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    Button buttonFind, buttonSave;
    ImageView imageResult;
    TextView textCaption;
    CardView cardResult;

    Context context;
    Profile profile;
    private MobileApplication app;
    private List<Profile> list;

    private Subscription subscription;

    public static HomeFragment newInstance(){
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MobileApplication) getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        context = getContext();
        list = app.getSelectedProfile();
        if(list==null){
            list = new ArrayList<>();
            app.setSelectedProfile(list);
        }

        cardResult = rootView.findViewById(R.id.card_result);
        imageResult = rootView.findViewById(R.id.image_result);
        textCaption = rootView.findViewById(R.id.textView_caption);
        buttonFind = rootView.findViewById(R.id.button_find);
        buttonSave = rootView.findViewById(R.id.button_save);

        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getProfile();
                getDataProfile();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isThere = false;
                for(int i=0; i<list.size() ;i++){
                    if(profile.getId().equals(list.get(i).getId())){
                        isThere = true;
                    }
                }
                if(!isThere){
                    list.add(profile);
                    app.setSelectedProfile(list);
                    Toast.makeText(context, "Berhasil menyimpan profil", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private void getProfile(){
        final GetProfileListener getProfileListener = new GetProfileListener();
        final String url = getProfileListener.URL_GET_PROFILE;
        final String method = BaseListener.METHOD_GET;

        getProfileListener.request(method, url, new BaseListener.OnRequestListener() {
            @Override
            public void onPreExecute() {
                getProfileListener.showProgressDialog(context);
            }

            @Override
            public void onPostExecute(JSONObject response) throws Exception {
                getProfileListener.hideProgressDialog();
                Log.d("baba", "response : " + response.toString());
                profile = getProfileListener.getDoProfile(response);
                setLayoutResult(profile);
            }
        });
    }

    private void setLayoutResult(Profile profileResult){
        cardResult.setVisibility(View.VISIBLE);
        buttonSave.setVisibility(View.VISIBLE);
        Picasso.with(context)
                .load(profileResult.getThumbnailSrc())
                .into(imageResult);
        textCaption.setText(profileResult.getCaption());
    }

    @Override
    public void onResume() {
        if(profile!=null){
            setLayoutResult(profile);
        }
        super.onResume();
    }

    private void getDataProfile(){
        final GetProfileListener getProfileListener = new GetProfileListener();
        getProfileListener.showProgressDialog(context);

        Observer<Profile> profileObserver = new Observer<Profile>() {
            @Override
            public void onCompleted() {
                Log.d("baba", "In onCompleted");
                getProfileListener.hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("baba", e.getMessage());
                getProfileListener.hideProgressDialog();
            }

            @Override
            public void onNext(Profile resultProfile) {
                Log.d("baba", "In onNext");
                profile = resultProfile;
                setLayoutResult(profile);
            }
        };

        subscription = SeanClient.getInstance()
                .getProfileData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profileObserver);

    }
}
