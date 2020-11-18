package com.ldm.cogetucita.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.ldm.cogetucita.fragments.*;

public class HelpPagerAdapter extends FragmentStateAdapter {

    public HelpPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Help1Fragment();
            default:
                return new Help2Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
