 package com.example.android.sitiservices.Profile;

 import android.content.Intent;
 import android.os.Bundle;
 import android.os.Handler;
 import android.os.Message;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.ImageView;
 import android.widget.TextView;

 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.appcompat.widget.Toolbar;
 import androidx.recyclerview.widget.GridLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;
 import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

 import com.bumptech.glide.Glide;
 import com.example.android.sitiservices.R;
 import com.example.android.sitiservices.Services.CompleteViewOfProduct;
 import com.example.android.sitiservices.Services.D_PastOrders;
 import com.example.android.sitiservices.Services.D_ProfileOfWorker;
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.database.ValueEventListener;

 import java.util.ArrayList;
 import java.util.Collections;

 public class A_MyOrders extends AppCompatActivity {
     interface OnCardViewItemClickListener
     {
         void onItemClickListenerOfCardView(int position);
     }
     Toolbar toolbar;
     TextView noOfpastOrders;
     RecyclerView recyclerView;
     ArrayList<String> keys=new ArrayList<>();
     ArrayList<D_ProfileOfWorker> arrayListForPastOrders=new ArrayList<>();
     MyAdapterForPastOrders myAdapter;
      SwipeRefreshLayout swipeRefreshLayout;
      DatabaseReference databaseReferenceforkey= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MyOrders");
      DatabaseReference databaseReferencefordata= FirebaseDatabase.getInstance().getReference("Users").child("Bookings");
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_a__my_orders);
         setRecyclerView();
         getSeriesOfMyOrdersData();
         getNoOfAddressOfPastOrders();
         findViewByIds();
         setSwipeRefreshLayout();
     }

     public void setSwipeRefreshLayout()
     {
         swipeRefreshLayout=findViewById(R.id.swipe_myOrders);
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
             Collections.shuffle(arrayListForPastOrders);
             myAdapter.notifyDataSetChanged();
             swipeRefreshLayout.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     swipeRefreshLayout.setRefreshing(false);
                 }
             }, 2000);
         }
     };
     private void setRecyclerView() {
         recyclerView=findViewById(R.id.MyOrders_RecyclerView);
         myAdapter = new MyAdapterForPastOrders(arrayListForPastOrders);
         GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
         recyclerView.setLayoutManager(gridLayoutManager);
         recyclerView.setHasFixedSize(true);
         recyclerView.setAdapter(myAdapter);
         myAdapter.setOnCardViewItemClickListener(new OnCardViewItemClickListener() {
             @Override
             public void onItemClickListenerOfCardView(int position) {
                 Intent intent=new Intent(getApplicationContext(), CompleteViewOfProduct.class);
                 intent.putExtra("ImageLocation",arrayListForPastOrders.get(position).WorkerImage);
                 intent.putExtra("Workername",arrayListForPastOrders.get(position).WorkerName);
                 intent.putExtra("Workermobile",arrayListForPastOrders.get(position).WorkerMobileNumber);
                 intent.putExtra("WorkerEmail",arrayListForPastOrders.get(position).WorkerEmail);
                 intent.putExtra("Workerbio",arrayListForPastOrders.get(position).WorkerBio);
                 intent.putExtra("Workeraddress",arrayListForPastOrders.get(position).WorkerAddress);
                 intent.putExtra("ProductCategoryByMaterial","Electrician");
                 intent.putExtra("ProductCategoryByGender","Workers");
//                 intent.putExtra("Quantity",arrayListForPastOrders.get(position).NoOfOrdersPurchased);
//                 intent.putExtra("Size",arrayListForPastOrders.get(position).ProductSize);
                 startActivity(intent);
             }
         });
     }

     private void findViewByIds() {
         toolbar = findViewById(R.id.Toolbar_MyOrders);
         toolbar.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onBackPressed();
             }
         });
         noOfpastOrders = findViewById(R.id.NumberOfPastOrders);
     }
    private void getNoOfAddressOfPastOrders()
    {
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MyOrders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    noOfpastOrders.setText(dataSnapshot.getChildrenCount()+" Past Orders");
                }
                else
                {
                    noOfpastOrders.setText("0 "+"Past Orders");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



     class MyAdapterForPastOrders extends RecyclerView.Adapter<MyAdapterForPastOrders.ViewHolderClass>
     {
         ArrayList<D_ProfileOfWorker> arrayList;
         OnCardViewItemClickListener onCardViewItemClickListener;
         MyAdapterForPastOrders(ArrayList<D_ProfileOfWorker> arrayList1)
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
             return arrayList.size();
         }

         private class ViewHolderClass extends RecyclerView.ViewHolder
         {
             TextView Name,Price;
             ImageView ProductImage;
             public ViewHolderClass(@NonNull View itemView) {
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
                             if (position!= RecyclerView.NO_POSITION)
                             {
                                 onCardViewItemClickListener.onItemClickListenerOfCardView(position);
                             }
                         }
                     }
                 });

             }
         }
     }


     private void getSeriesOfMyOrdersData() {
       databaseReferenceforkey.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists())
               {
                  for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                  {
                     if (dataSnapshot1.exists())
                     {
                         databaseReferencefordata.child(dataSnapshot1.getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                             {
                                 if (dataSnapshot.exists()) {
                                     String workername=dataSnapshot.child("Workername").getValue(String.class);
                                     String workermobile=dataSnapshot.child("Workermobile").getValue(String.class);
                                     String workeremail=dataSnapshot.child("WorkerEmail").getValue(String.class);
                                     String workerbio=dataSnapshot.child("Workerbio").getValue(String.class);
                                     String workeraddress=dataSnapshot.child("Workeraddress").getValue(String.class);
                                     String workerimage=dataSnapshot.child("ImageLocation").getValue(String.class);
                                     D_ProfileOfWorker dShoesDataFromInternet=new D_ProfileOfWorker(workername,workermobile,workeremail,workerbio,workeraddress,workerimage);
                                     arrayListForPastOrders.add(dShoesDataFromInternet);
                                 }
                                 else
                                     Log.e("A_MyOrders","Unable to get data form exact location");
                                 }
                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });
                     }
                     else
                     {
                         Log.e("A_MyOrders"," Datasnapshot does not exist Unable to key");
                     }
                  }
               }
               else
               {
                  Log.e("A_MyOrders"," Datasnapshot does not exist Unable to key in myorders ");
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
       myAdapter.notifyDataSetChanged();
     }
 }
