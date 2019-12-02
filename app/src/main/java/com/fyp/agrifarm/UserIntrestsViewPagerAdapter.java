package com.fyp.agrifarm;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class UserIntrestsViewPagerAdapter extends FragmentPagerAdapter {
    public UserIntrestsViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new IntrestsFragment();
        }
        else if (position == 1)
        {
            fragment = new FollowersFragment();
        }
        else if (position == 2)
        {
            fragment = new FollowingFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Interests";
        }
        else if (position == 1)
        {
            title = "Followers";
        }
        else if (position == 2)
        {
            title = "Following";
        }
        return title;
    }

}
