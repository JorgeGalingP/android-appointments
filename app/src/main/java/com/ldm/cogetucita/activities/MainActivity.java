package com.ldm.cogetucita.activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ldm.cogetucita.R;
import com.ldm.cogetucita.adapters.AppointmentPagerAdapter;
import com.ldm.cogetucita.models.State;
import com.ldm.cogetucita.repositories.AppointmentRepository;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }

        // set repository
        final AppointmentRepository appointmentRepository = new AppointmentRepository(this);

        // set ViewPager2
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new AppointmentPagerAdapter(this));

        // set TabLayout
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

                        int n = appointmentRepository.findAllAppointmentsByState(State.CONFIRMED).size();

                        if (n > 0){
                            BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                            badgeDrawable.setBackgroundColor(
                                    ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)
                            );
                            badgeDrawable.setVisible(true);
                            badgeDrawable.setNumber(n);
                        }
                        break;
                    }
                    case 2: {
                        tab.setText("Terminados");
                        tab.setIcon(R.drawable.ic_done);

                        int n = appointmentRepository.findAllAppointmentsByState(State.DONE).size();

                        if (n > 0){
                            BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                            badgeDrawable.setBackgroundColor(
                                    ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)
                            );
                            badgeDrawable.setVisible(true);
                            badgeDrawable.setNumber(n);
                        }
                        break;
                    }
                }
            }
        });
        tabLayoutMediator.attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                TabLayout.Tab tab = tabLayout.getTabAt(position);
                if (tab != null){
                    BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                    badgeDrawable.setVisible(false);
                }
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                // go to the main activity
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(homeIntent);
                finish();

                return true;
            case R.id.help:
                // go to the main activity
                Intent helpIntent = new Intent(this, HelpActivity.class);
                helpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(helpIntent);
                finish();

                return true;
            case R.id.about:
                Toast.makeText(MainActivity.this, "Selected about!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}