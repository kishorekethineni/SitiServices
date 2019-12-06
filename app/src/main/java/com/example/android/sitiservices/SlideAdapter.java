package com.example.android.sitiservices;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

class SlideAdapter extends PagerAdapter {

    public SlideAdapter(Context context) {
        this.context = context;
    }

    private Context context;
    LayoutInflater inflater;

  //  public int[] images = {R.drawable.ser, R.drawable.ceiling,R.drawable.clean
  //  };
    public int[] images1 = {R.drawable.four, R.drawable.three,R.drawable.homeservicetwo
    };





    @Override
    public int getCount() {
        return  images1.length;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.slideshow, container, false);


        ImageView img = (ImageView) view.findViewById(R.id.img1);

        img.setImageResource(images1[position]);


        container.addView(view);


       return view;


    }




    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);


    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout) object);
    }
}
