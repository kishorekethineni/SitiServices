package com.example.android.sitiservices.Services;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.android.sitiservices.R;
import com.google.android.material.tabs.TabLayout;

import static java.security.AccessController.getContext;

public class Service_Holder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service__holder);
        ViewPager viewPager=findViewById(R.id.ViewPagerInMenFragment);
        viewPager.setAdapter(new MyAdapterForMenFragment(getChildFragmentManager()));
        TabLayout tabLayout=findViewById(R.id.TabLayoutInMenFragment);
        tabLayout.setupWithViewPager(viewPager);
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
                    return new FormalShoe(getContext());
                case 1:
                    return new SneakersShoe(getContext());
                case 2:
                    return new SportsShoe(getContext());
                case 3:
                    return new SmartShoe(getContext());
                case 4:
                    return new EthnicShoe(getContext());
                case 5:
                    return new CasualShoe(getContext());

            }
            return null;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String Name=null;
            switch (position) {
                case 0:
                    Name="Formal";
                    break;
                case 1:
                    Name="Sneakers";
                    break;
                case 2:
                    Name="Sports";
                    break;
                case 3:
                    Name="Smart";
                    break;
                case 4:
                    Name="Ethnic";
                    break;
                case 5:
                    Name="Casual";
                    break;
            }
            return Name;
        }
    }
}
