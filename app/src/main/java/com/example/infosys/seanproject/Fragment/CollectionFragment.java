package com.example.infosys.seanproject.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.infosys.seanproject.Adapter.CustomGridAdapter;
import com.example.infosys.seanproject.Application.MobileApplication;
import com.example.infosys.seanproject.Class.Profile;
import com.example.infosys.seanproject.R;

import java.util.List;

public class CollectionFragment extends Fragment {

    CustomGridAdapter customGridAdapter;
    Context context;
    private MobileApplication app;
    private List<Profile> listProfile;

    GridView gridView;

    public static CollectionFragment newInstance(){
        CollectionFragment collectionFragment = new CollectionFragment();
        return collectionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MobileApplication) getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collection, container, false);

        context = getContext();
        listProfile = app.getSelectedProfile();

        gridView = rootView.findViewById(R.id.gridView_collection);
        customGridAdapter = new CustomGridAdapter(context, listProfile);
        gridView.setAdapter(customGridAdapter);

        return rootView;
    }

}
