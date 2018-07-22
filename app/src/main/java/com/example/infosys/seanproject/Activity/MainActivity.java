package com.example.infosys.seanproject.Activity;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.infosys.seanproject.Fragment.CollectionFragment;
import com.example.infosys.seanproject.Fragment.HomeFragment;
import com.example.infosys.seanproject.R;

public class MainActivity extends FragmentActivity {

    HomeFragment homeFragment;
    CollectionFragment collectionFragment;
    ImageView imageCollection, imageBack;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageBack = findViewById(R.id.image_back);
        imageCollection = findViewById(R.id.image_collection);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHomePage();
            }
        });
        imageCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCollectionPage();
            }
        });
        loadHomePage();
    }

    private void loadHomePage(){
        imageBack.setVisibility(View.GONE);
        imageCollection.setVisibility(View.VISIBLE);
        if(homeFragment==null){
            homeFragment = HomeFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment, homeFragment).commit();
    }

    private void loadCollectionPage(){
        imageBack.setVisibility(View.VISIBLE);
        imageCollection.setVisibility(View.GONE);
        if(collectionFragment==null){
            collectionFragment = CollectionFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment, collectionFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if(homeFragment.isVisible()){
            if(doubleBackToExitPressedOnce){
                super.onBackPressed();
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

}
