package com.example.android.sitiservices.Services;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.sitiservices.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class Driver extends Fragment {
    interface OnCardViewItemClickListener
    {
        void onItemClickListenerOfCardView(int position);
    }


    ArrayList<D_ProfileOfWorker> arrayListSneakers=new ArrayList<>();
    ArrayList<String> stringArrayList=new ArrayList<>();
    Context context;
    MyAdapterForSneakersMen myAdapterSneakers;
    SwipeRefreshLayout swipeRefreshLayout;
    Driver(Context context)
    {
        this.context=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=LayoutInflater.from(context).inflate(R.layout.activity_driver,container,false);
        setSwipeRefreshLayout(view);
        getArrayListData();
        setRecyclerView(view);
        return view;
    }
    public void setSwipeRefreshLayout(View view)
    {
        swipeRefreshLayout=view.findViewById(R.id.swipe_men_sneakers);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        mHandler.sendEmptyMessage(0);
                    }
                }, 1000);
            }
        });
    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            Collections.shuffle(arrayListSneakers);
            myAdapterSneakers.notifyDataSetChanged();
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };
    public void setRecyclerView(View view)
    {
        RecyclerView recyclerView=view.findViewById(R.id.RecyclerView_Sneakers_Men);
        GridLayoutManager manager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        myAdapterSneakers=new MyAdapterForSneakersMen(arrayListSneakers);
        recyclerView.setAdapter(myAdapterSneakers);
        myAdapterSneakers.setOnCardViewItemClickListener(new OnCardViewItemClickListener() {
            @Override
            public void onItemClickListenerOfCardView(int position) {
                Intent intent=new Intent(getContext(),CompleteViewOfProduct.class);
                intent.putExtra("ProductLink",stringArrayList.get(position));
                intent.putExtra("ImageLocation",arrayListSneakers.get(position).WorkerImage);
                intent.putExtra("Workername",arrayListSneakers.get(position).WorkerName);
                intent.putExtra("Workermobile",arrayListSneakers.get(position).WorkerMobileNumber);
                intent.putExtra("WorkerEmail",arrayListSneakers.get(position).WorkerEmail);
                intent.putExtra("Workerbio",arrayListSneakers.get(position).WorkerBio);
                intent.putExtra("Workeraddress",arrayListSneakers.get(position).WorkerAddress);
                intent.putExtra("ProductCategoryByMaterial","Driver");
                intent.putExtra("ProductCategoryByGender","Workers");
                startActivity(intent);
            }
        });
    }
    public void getArrayListData()
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Workers").child("Driver");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayListSneakers.clear();
                stringArrayList.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    if (dataSnapshot1.exists())
                    {
                        stringArrayList.add(dataSnapshot1.getKey());
                        D_ProfileOfWorker d_profileOfWorker=dataSnapshot1.getValue(D_ProfileOfWorker.class);
                        Log.e("Hey",d_profileOfWorker.WorkerName);
                        arrayListSneakers.add(d_profileOfWorker);
                    }
                    else
                        Log.e("Hey","Datasnapshot does not exist!!");
                }
                myAdapterSneakers.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    class MyAdapterForSneakersMen extends RecyclerView.Adapter<MyAdapterForSneakersMen.ViewHolderClass>
    {

        ArrayList<D_ProfileOfWorker> arrayList;
        OnCardViewItemClickListener onCardViewItemClickListener;
        public MyAdapterForSneakersMen(ArrayList<D_ProfileOfWorker> arrayList1)
        {
            this.arrayList=arrayList1;
        }
        public void setOnCardViewItemClickListener(OnCardViewItemClickListener onCardViewItemClickListener1)
        {
            this.onCardViewItemClickListener=onCardViewItemClickListener1;
        }
        @NonNull
        @Override
        public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view=LayoutInflater.from(getContext()).inflate(R.layout.single_view_for_label_of_shoe,parent,false);
            return new ViewHolderClass(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {

            holder.Name.setText(arrayList.get(position).WorkerName);
//            holder.Price.setText(arrayList.get(position).ProductPriceOfShoe);
            Glide.with(getContext()).load(arrayList.get(position).WorkerImage).into(holder.ProductImage);
        }


        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        private class ViewHolderClass extends RecyclerView.ViewHolder
        {
            TextView Name,Price;
            ImageView ProductImage;
            public ViewHolderClass(@NonNull View itemView)
            {
                super(itemView);
                Name=itemView.findViewById(R.id.SingleViewForLabelOfShoe_Name);
                Price=itemView.findViewById(R.id.SingleViewForLabelOfShoe_Price);
                ProductImage=itemView.findViewById(R.id.SingleViewForLabelOfShoe_ImageView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCardViewItemClickListener!=null)
                        {
                            int position=getAdapterPosition();
                            if (position!=RecyclerView.NO_POSITION)
                            {
                                onCardViewItemClickListener.onItemClickListenerOfCardView(position);
                            }
                        }
                    }
                });
            }
        }
    }
}
