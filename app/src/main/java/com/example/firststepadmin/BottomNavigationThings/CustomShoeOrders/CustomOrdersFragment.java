package com.example.firststepadmin.BottomNavigationThings.CustomShoeOrders;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firststepadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomOrdersFragment extends Fragment {


    public CustomOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_orders, container, false);
    }

}