package com.example.android.sitiservices;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.android.sitiservices.Services.Amc;
import com.example.android.sitiservices.Services.Carpenter;
import com.example.android.sitiservices.Services.Electrician;
import com.example.android.sitiservices.Services.Plumber;
import com.example.android.sitiservices.Services.Refrigirator;
import com.example.android.sitiservices.Services.Washing;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
ViewPager viewPager;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SlideAdapter adapter=new SlideAdapter(this);
        SlideAdapter adapter1=new SlideAdapter(this);
        viewPager=findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 4000);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    break;
                case R.id.navigation_help:
                    toolbar.setTitle("Help Center");
                    break;
                case R.id.navigation_profile:
                    toolbar.setTitle("Profile");

                   break;
                           }
            return false;
        }
    };


    public void Search(View view) {
        Intent i=new Intent(getApplicationContext(), Search.class);
        startActivity(i);
    }

    public void Painter(View view) {
        Intent i=new Intent(getApplicationContext(), Painter.class);
        startActivity(i);
    }

    public void BabbySitter(View view) {
        Intent i=new Intent(getApplicationContext(), BabbySitter.class);
        startActivity(i);
    }

    public void Driver(View view) {
        Intent i=new Intent(getApplicationContext(), Driver.class);
        startActivity(i);
    }

    public void Builder(View view) {
        Intent i=new Intent(getApplicationContext(), Builder.class);
        startActivity(i);
    }

    public void Cook(View view) {
        Intent i=new Intent(getApplicationContext(), Cook.class);
        startActivity(i);
    }

    public void Electrician(View view) {
        Intent i=new Intent(getApplicationContext(), Electrician.class);
        startActivity(i);
    }

    public void Plumber(View view) {
        Intent i=new Intent(getApplicationContext(), Plumber.class);
        startActivity(i);
    }

    public void Carpenter(View view) {
        Intent i=new Intent(getApplicationContext(), Carpenter.class);
        startActivity(i);
    }

    public void Refrigirator(View view) {
        Intent i=new Intent(getApplicationContext(), Refrigirator.class);
        startActivity(i);
    }

    public void Washing(View view) {
        Intent i=new Intent(getApplicationContext(), Washing.class);
        startActivity(i);
    }

    public void AMC(View view) {
        Intent i=new Intent(getApplicationContext(), Amc.class);
        startActivity(i);
    }

    public void Donateblood(View view) {
        Intent i=new Intent(getApplicationContext(), Donateblood.class);
        startActivity(i);
    }

    public void Donateplant(View view) {
        Intent i=new Intent(getApplicationContext(), Donateplant.class);
        startActivity(i);
    }


    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem()==0) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else if(viewPager.getCurrentItem()==1)
                {
                        viewPager.setCurrentItem(2);
                    }
                    else {
                        viewPager.setCurrentItem(3);
                    }
                }
            });
        }
    }
}




