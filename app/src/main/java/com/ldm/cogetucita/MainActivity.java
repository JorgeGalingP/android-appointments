package com.ldm.cogetucita;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ldm.cogetucita.adapters.AppointmentPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }

        viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new AppointmentPagerAdapter(this));

        tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: {
                        tab.setText("Pendientes");
                        tab.setIcon(R.drawable.ic_pending);
                        break;
                    }
                    case 1: {
                        tab.setText("Confirmados");
                        tab.setIcon(R.drawable.ic_confirmed);
                        break;
                    }
                    case 2: {
                        tab.setText("Terminados");
                        tab.setIcon(R.drawable.ic_done);
                        break;
                    }
                }
            }
        });
        tabLayoutMediator.attach();

        // set Fab
        FloatingActionButton fab = findViewById(R.id.addFab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });
    }
}