package com.example.android.sitiservices;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.android.sitiservices.Profile.ProfileSection;
import com.example.android.sitiservices.Services.Amc;
import com.example.android.sitiservices.Services.Carpenter;
import com.example.android.sitiservices.Services.Electrician;
import com.example.android.sitiservices.Services.Plumber;
import com.example.android.sitiservices.Services.Refrigirator;
import com.example.android.sitiservices.Services.Service_Holder;
import com.example.android.sitiservices.Services.Washing;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
ViewPager viewPager;
    ImageView profile,customercare,help,feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile=findViewById(R.id.profileicon);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prof=new Intent(MainActivity.this, ProfileSection.class);
                startActivity(prof);
            }
        });
        SlideAdapter adapter=new SlideAdapter(this);
        SlideAdapter adapter1=new SlideAdapter(this);
        viewPager=findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 4000);
    }



    public void Search(View view) {
//        Intent i=new Intent(getApplicationContext(), Service_Holder.class);
//        i.putExtra("Type","Search");
//        startActivity(i);
    }

    public void Painter(View view) {
        Intent i=new Intent(getApplicationContext(), Service_Holder.class);
        i.putExtra("Type","Painter");
        startActivity(i);
    }

    public void BabbySitter(View view) {
        Intent i=new Intent(getApplicationContext(), Service_Holder.class);
        i.putExtra("Type","BabySitter");
        startActivity(i);
    }

    public void Driver(View view) {
        Intent i=new Intent(getApplicationContext(), Service_Holder.class);
        i.putExtra("Type","Driver");
        startActivity(i);
    }

    public void Builder(View view) {
        Intent i=new Intent(getApplicationContext(), Service_Holder.class);
        i.putExtra("Type","Builder");
        startActivity(i);
    }

    public void Cook(View view) {
        Intent i=new Intent(getApplicationContext(), Service_Holder.class);
        i.putExtra("Type","Cook");
        startActivity(i);
    }

    public void Electrician(View view) {
        Intent i=new Intent(getApplicationContext(), Service_Holder.class);
        i.putExtra("Type","Electrician");
        startActivity(i);
    }

    public void Plumber(View view) {
        Intent i=new Intent(getApplicationContext(), Service_Holder.class);
        i.putExtra("Type","Plumber");
        startActivity(i);
    }

    public void Carpenter(View view) {
        Intent i=new Intent(getApplicationContext(), Service_Holder.class);
        i.putExtra("Type","Carpenter");
        startActivity(i);
    }

    public void Refrigirator(View view) {
        Intent i=new Intent(getApplicationContext(), Service_Holder.class);
        i.putExtra("Type","Refrigirator");
        startActivity(i);
    }

    public void Washing(View view) {
        Intent i=new Intent(getApplicationContext(), Service_Holder.class);
        i.putExtra("Type","Washing");
        startActivity(i);
    }

    public void AMC(View view) {
        Intent i=new Intent(getApplicationContext(), Service_Holder.class);
        i.putExtra("Type","AMC");
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

    public void customercare(View view)
    {
        Intent i=new Intent(MainActivity.this,Chatbot.class);
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




