package com.vetonline;
 
import com.vetonline.fragments.PetStatusFragment;
import com.vetonline.fragments.FragmentTab2;
import com.vetonline.fragments.FragmentTab3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class ViewPagerAdapter extends FragmentPagerAdapter {
 
    // Declare the number of ViewPager pages
    final int PAGE_COUNT = 3;
 
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int arg0) {
        switch (arg0) {
 
        // Open FragmentTab1.java
        case 0:
            PetStatusFragment fragmenttab1 = new PetStatusFragment();
            return fragmenttab1;
 
        // Open FragmentTab2.java
        case 1:
            FragmentTab2 fragmenttab2 = new FragmentTab2();
            return fragmenttab2;
 
        // Open FragmentTab3.java
        case 2:
            FragmentTab3 fragmenttab3 = new FragmentTab3();
            return fragmenttab3;
        }
        return null;
    }
 
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return PAGE_COUNT;
    }
 
}