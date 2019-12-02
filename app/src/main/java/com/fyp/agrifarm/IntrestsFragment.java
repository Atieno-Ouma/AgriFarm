package com.fyp.agrifarm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.fyp.agrifarm.beans.UserIntrestData;

import java.util.ArrayList;

public class IntrestsFragment extends Fragment {
    RecyclerView userintrestrv;
    private UserIntrestRecyclerViewAdapter adapter;
    ArrayList<UserIntrestData> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout parent = (FrameLayout) inflater.inflate(R.layout.fragment_intrests, container, false);
        userintrestrv = parent.findViewById(R.id.userintrestrv);
        list = new ArrayList<>();
        list.add(new UserIntrestData(R.drawable.ic_apple,"Apple"));
        list.add(new UserIntrestData(R.drawable.ic_carrot,"Carrot"));
        list.add(new UserIntrestData(R.drawable.ic_grape,"Grape"));
        list.add(new UserIntrestData(R.drawable.ic_guava,"Gauva"));
        list.add(new UserIntrestData(R.drawable.ic_orange,"Orange"));
        list.add(new UserIntrestData(R.drawable.ic_bringle,"Brinjal"));
        list.add(new UserIntrestData(R.drawable.ic_wmelon,"Watermelon"));
        adapter = new UserIntrestRecyclerViewAdapter(getContext(),list);
        userintrestrv.setAdapter(adapter);








        return parent;

    }


}
