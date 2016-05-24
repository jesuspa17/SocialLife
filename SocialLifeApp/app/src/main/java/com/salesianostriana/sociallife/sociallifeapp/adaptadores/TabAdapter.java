package com.salesianostriana.sociallife.sociallifeapp.adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Jesus Pallares on 25/04/2016.
 */
public class TabAdapter extends FragmentPagerAdapter {

    private int num_tabs;
    private String tab1;
    private String tab2;
    private Fragment frag1,frag2;

    private int tab_1;
    private int tab_2;


    public TabAdapter(int num_tabs, FragmentManager fm, String tab1, String tab2,Fragment frag1, Fragment frag2) {
        super(fm);
        this.num_tabs = num_tabs;
        this.tab1=tab1;
        this.tab2=tab2;
        this.frag1 = frag1;
        this.frag2 = frag2;
    }


    @Override
    public Fragment getItem(int position)
    {
        switch (position){
            case 0 : return frag1;
            case 1 : return frag2;
        }
        return null;
    }

    @Override
    public int getCount() {
        return num_tabs;
    }



    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0 :
                return tab1;
            case 1 :
                return tab2;
        }
        return null;
    }
}