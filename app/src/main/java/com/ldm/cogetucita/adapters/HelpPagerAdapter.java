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
            case 1:
                return new Help2Fragment();
            case 2:
                return new Help3Fragment();
            default:
                return new Help4Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
