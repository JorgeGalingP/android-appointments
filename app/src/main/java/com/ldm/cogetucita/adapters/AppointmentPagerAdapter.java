package com.ldm.cogetucita.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.ldm.cogetucita.fragments.ConfirmedFragment;
import com.ldm.cogetucita.fragments.DoneFragment;
import com.ldm.cogetucita.fragments.PendingFragment;

public class AppointmentPagerAdapter extends FragmentStateAdapter {

    public AppointmentPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PendingFragment();
            case 1:
                return new ConfirmedFragment();
            default:
                return new DoneFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
