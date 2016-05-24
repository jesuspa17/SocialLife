package com.salesianostriana.sociallife.sociallifeapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salesianostriana.sociallife.sociallifeapp.R;
import com.salesianostriana.sociallife.sociallifeapp.adaptadores.TabAdapter;


public class GenteFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int num_tabs = 2;

    public GenteFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gente,null);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);


        viewPager.setAdapter(new TabAdapter(num_tabs,getChildFragmentManager(),"Seguido","Explorar",new MisAmigosFragment(),new ExplorarFragment()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.getTabAt(0).setText(R.string.seguidos);
                tabLayout.getTabAt(1).setText(R.string.explorar);
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
