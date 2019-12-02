package com.fyp.agrifarm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.agrifarm.beans.WeatherDailyForecast;
import com.fyp.agrifarm.beans.WeatherHourlyForecast;

import java.util.Arrays;

public class WeatherFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "WeatherFragment";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public WeatherFragment() {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.content_weather, container,
                false);

        parent.findViewById(R.id.layout_rel).setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        RecyclerView rvHourlyForecast = parent.findViewById(R.id.rvHourlyForecast);
        WeatherHourlyRecyclerAdapter hourlyRecyclerAdapter =
                new WeatherHourlyRecyclerAdapter(getContext(), Arrays.asList(
                        new WeatherHourlyForecast("2 PM", "19"),
                        new WeatherHourlyForecast("5 PM", "18"),
                        new WeatherHourlyForecast("8 PM", "17"),
                        new WeatherHourlyForecast("11 PM", "19"),
                        new WeatherHourlyForecast("2 AM", "16"),
                        new WeatherHourlyForecast("5 AM", "20"),
                        new WeatherHourlyForecast("8 AM", "21"),
                        new WeatherHourlyForecast("11 AM", "25")
                ));
        rvHourlyForecast.setAdapter(hourlyRecyclerAdapter);

        RecyclerView rvDailyForecast = parent.findViewById(R.id.rvDailyForecast);
        WeatherDailyRecyclerAdapter dailyRecyclerAdapter =
                new WeatherDailyRecyclerAdapter(getContext(), Arrays.asList(
                        new WeatherDailyForecast("SUNDAY", "19", "SUNNY"),
                        new WeatherDailyForecast("MONDAY", "19", "SUNNY"),
                        new WeatherDailyForecast("TUESDAY", "19", "SUNNY"),
                        new WeatherDailyForecast("WEDNESDAY", "19", "SUNNY"),
                        new WeatherDailyForecast("THURSDAY", "19", "SUNNY"),
                        new WeatherDailyForecast("FRIDAY", "19", "SUNNY")
                ));
        rvDailyForecast.setAdapter(dailyRecyclerAdapter);

        return parent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onItemClick(View v);
    }
}
