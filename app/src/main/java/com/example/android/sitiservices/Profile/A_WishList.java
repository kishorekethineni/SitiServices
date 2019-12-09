package com.example.android.sitiservices.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.sitiservices.R;
import com.example.android.sitiservices.Services.CompleteViewOfProduct;
import com.example.android.sitiservices.Services.D_ProfileOfWorker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class A_WishList extends AppCompatActivity {

    interface OnCardViewItemClickListener
    {
        void onItemClickListenerOfCardView(int position);
    }
    RecyclerView recyclerView;
    TextView noOfWishListed;
    MyAdapterForWishList myAdapterForWishList;
    ArrayList<D_ProfileOfWorker> arrayList=new ArrayList<>();
    Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<String> D_ProductCategoryByGender=new ArrayList<>();
    ArrayList<String>D_ProductLink=new ArrayList<>();
    ArrayList<String>D_ProductCategoryByMaterial=new ArrayList<>();
    ArrayList<String>D_Workername=new ArrayList<>();
    ArrayList<String>D_Mobileno=new ArrayList<>();
    ArrayList<String>D_email=new ArrayList<>();
    ArrayList<String>D_bio=new ArrayList<>();
    ArrayList<String>D_address=new ArrayList<>();
    ArrayList<String>D_Image=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__wish_list);
        setNoOfWishListed();
        getNoOfWishListedProducts();
        setToolBar();
        setSwipeRefreshLayout();
        setRecyclerView();
        getArrayListData();
    }
    private void setNoOfWishListed()
    {
        noOfWishListed=findViewById(R.id.NoOf_WishListed_Products);
    }

    private void setSwipeRefreshLayout()
    {
        swipeRefreshLayout=findViewById(R.id.swipe_wishlist);
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
            Collections.shuffle(arrayList);
            myAdapterForWishList.notifyDataSetChanged();
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };
    private void setToolBar() {
        toolbar=findViewById(R.id.Toolbar_WishList);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setRecyclerView()
    {
        recyclerView=findViewById(R.id.RecyclerView_wishList);
        GridLayoutManager linearLayoutManager=new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        myAdapterForWishList=new MyAdapterForWishList(arrayList);
        recyclerView.setAdapter(myAdapterForWishList);

        myAdapterForWishList.setOnCardViewItemClickListener(new OnCardViewItemClickListener() {
            @Override
            public void onItemClickListenerOfCardView(int position) {
                Intent intent=new Intent(getApplicationContext(), CompleteViewOfProduct.class);
                intent.putExtra("ProductLink",D_ProductLink.get(position));

                intent.putExtra("Workername",D_ProductCategoryByMaterial.get(position));
                intent.putExtra("Workermobile",D_ProductCategoryByGender.get(position));

                startActivity(intent);
            }
        });
    }
    private void getArrayListData()
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MyWishList");
        final DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        String pg=dataSnapshot1.child("ProductCategoryByGender").getValue(String.class),pm=dataSnapshot1.child("ProductCategoryByMaterial").getValue(String.class),pl=dataSnapshot1.child("ProductLink").getValue(String.class);
                        D_ProductCategoryByGender.add(pg);
                        D_ProductCategoryByMaterial.add(pm);
                        D_ProductLink.add(pl);

                        databaseReference1.child(pg).child(pm).child(pl)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists())
                                        {
                                            String workername=dataSnapshot.child("Workername").getValue(String.class);
                                            String workermobile=dataSnapshot.child("Workermobile").getValue(String.class);
                                            String workeremail=dataSnapshot.child("WorkerEmail").getValue(String.class);
                                            String workerbio=dataSnapshot.child("Workerbio").getValue(String.class);
                                            String workeraddress=dataSnapshot.child("Workeraddress").getValue(String.class);
                                            String workerimage=dataSnapshot.child("ImageLocation").getValue(String.class);
                                            D_ProfileOfWorker dShoesDataFromInternet=new D_ProfileOfWorker(workername,workermobile,workeremail,workerbio,workeraddress,workerimage);
                                            arrayList.add(dShoesDataFromInternet);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                    }
                    myAdapterForWishList.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getNoOfWishListedProducts()
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MyWishList");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    noOfWishListed.setText("You have "+dataSnapshot.getChildrenCount()+" Products in WishList");
                }
                else
                    noOfWishListed.setText("You have 0 wishListed products");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class MyAdapterForWishList extends RecyclerView.Adapter<MyAdapterForWishList.ViewHolderClass>
    {
        ArrayList<D_ProfileOfWorker> cartOrdersArrayList;
        OnCardViewItemClickListener onCardViewItemClickListener;

        public MyAdapterForWishList(ArrayList<D_ProfileOfWorker> arrayList1)
        {
            this.cartOrdersArrayList=arrayList1;
        }
        public void setOnCardViewItemClickListener(OnCardViewItemClickListener onCardViewItemClickListener1)
        {
            this.onCardViewItemClickListener=onCardViewItemClickListener1;
        }
        @NonNull
        @Override
        public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolderClass(LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_view_for_label_of_shoe,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
            holder.Name.setText(arrayList.get(position).WorkerName);
            holder.Price.setText(arrayList.get(position).WorkerMobileNumber);
            Glide.with(getApplicationContext()).load(arrayList.get(position).WorkerImage).into(holder.ProductImage);
        }

        @Override
        public int getItemCount() {
            return cartOrdersArrayList.size();
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
