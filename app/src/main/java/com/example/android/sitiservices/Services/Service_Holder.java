package com.example.android.sitiservices.Services;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.sitiservices.R;
import com.google.android.material.tabs.TabLayout;

import static java.security.AccessController.getContext;

public class Service_Holder extends AppCompatActivity {

    String type;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__holder);
         viewPager=findViewById(R.id.ViewPagerInMenFragment);
        viewPager.setAdapter(new MyAdapterForMenFragment(getSupportFragmentManager()));
         tabLayout=findViewById(R.id.TabLayoutInMenFragment);
        tabLayout.setupWithViewPager(viewPager);
        getDataFromIntent();
    }
    private void getDataFromIntent()
    {
        int index=0;
        Intent intent=getIntent();
        type=intent.getStringExtra("Type");
        if (type.equals("Amc"))
        {
           index=0;
        }
        else if (type.equals("Electrician"))
        {
            index=1;
        }
        else if (type.equals("Plumber"))
        {
            index=2;
        }
        else if (type.equals("Refrigirator"))
        {
            index=3;
        }
        else if (type.equals("Carpenter"))
        {
            index=4;
        }
        else if (type.equals("Washing"))
        {
            index=5;
        }
        else if (type.equals("Painter"))
        {
            index=6;
        }
        else if (type.equals("Cook"))
        {
            index=7;
        }
        else if (type.equals("Driver"))
        {
            index=8;
        }
        else if (type.equals("BabySitter"))
        {
            index=9;
        }
        else if (type.equals("Builder"))
        {
            index=10;
        }
        viewPager.setCurrentItem(index);

    }
    private class MyAdapterForMenFragment extends FragmentPagerAdapter {

        public MyAdapterForMenFragment(@NonNull FragmentManager fm)
        {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return new Amc(getApplicationContext());
                case 1:
                    return new Electrician(getApplicationContext());
                case 2:
                    return new Plumber(getApplicationContext());
                case 3:
                    return new Refrigirator(getApplicationContext());
                case 4:
                    return new Carpenter(getApplicationContext());
                case 5:
                    return new Washing(getApplicationContext());
                case 6:
                    return new Painter(getApplicationContext());
                case 7:
                    return new Cook(getApplicationContext());
                case 8:
                    return new Driver(getApplicationContext());
                case 9:
                    return new BabbySitter(getApplicationContext());
                case 10:
                    return new Builder(getApplicationContext());
                }
            return null;
        }

        @Override
        public int getCount() {
            return 11;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String Name=null;
            switch (position) {
                case 0:
                    Name="Amc";
                    break;
                case 1:
                    Name="Electrician";
                    break;
                case 2:
                    Name="Plumber";
                    break;
                case 3:
                    Name="Refrigirator";
                    break;
                case 4:
                    Name="Carpenter";
                    break;
                case 5:
                    Name="Washing";
                    break;
                case 6:
                    Name="Painter";
                    break;
                case 7:
                    Name="Cook";
                    break;
                case 8:
                    Name="Driver";
                    break;
                case 9:
                    Name="BabySitter";
                    break;
                case 10:
                    Name="Builder";
                    break;
            }
            return Name;
        }
    }
}
