package com.example.android.sitiservices.Services;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.sitiservices.Login.A_SignIn;
import com.example.android.sitiservices.Login.D_CurrentUser;
import com.example.android.sitiservices.Profile.A_MyOrders;
import com.example.android.sitiservices.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CompleteViewOfProduct extends AppCompatActivity implements View.OnClickListener {
    private static final String CHANNEL_1_ID ="1" ;
    // todo use imageswitcher
    ImageView ProductImage,Bookmark;
    TextView mobno,name,email,bio,address,worker_catogeory;

    Button AddToCart;
    ImageButton Quantity_Increment,Quantity_Decrement;
    RadioGroup radioGroup;
    LinearLayout linearLayout;
    //////////////
    String D_name;
    String D_catoegory;
    String D_email;
    String D_mobile;
    String D_bio;
    String D_Image;
    String D_address;
    ///////////////
    String D_ProductCategoryByGender;
    Toolbar toolbar;
    boolean isBookMarked=false;
    String CurrentBookMarkedId;
    String payeeAddress = "9505123217@ybl";
    String payeeName = "Siti Services";
    String transactionNote = "Siti Services In App";
    String amount;
    String currencyUnit = "INR";
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Date date = new Date();
    String D_Date=formatter.format(date);
    String D_OrderId= D_CurrentUser.getPhone()+""+System.currentTimeMillis();
    Boolean isStored=false,isInCart=false;
    ListView listView;
    String D_address_For_Shipping,CurrentInCartId;
    ArrayList<String> addressArrayList=new ArrayList<>();
    ArrayAdapter adapter;
    String ProductLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_view_of_product);
        setListViewForAddress();

        getDataOfAddress();
        findViewByIds();
        gettingDataFromIntent();
        checkAlreadyBookMarked();
        settingDataIntoViews();
        createNotifications();
        checkAlreadyInCart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            AlertDialog.Builder  alertDialog;
            final AlertDialog alertDialog1;
            alertDialog=new AlertDialog.Builder(getApplicationContext());
            alertDialog.setMessage("You did logged in Please log in?");
            alertDialog.setTitle("Do not have account?");
            alertDialog.setPositiveButton("Goto Signin", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(getApplicationContext(), A_SignIn.class);
                    intent.putExtra("Number",0);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    onBackPressed();
                }
            });
            alertDialog.setCancelable(false);
            alertDialog1=alertDialog.create();
            alertDialog1.show();
        }
    }

    private void findViewByIds()
    {
        linearLayout =findViewById(R.id.linearLayout_CompleteProduct);
        ProductImage=findViewById(R.id.ImageOfProduct_Custom);
        worker_catogeory=findViewById(R.id.ProductMaterial_CompleteViewOfProduct);
        name=findViewById(R.id.NameOfWorker);
        mobno=findViewById(R.id.Mobno_of_worker);
        email=findViewById(R.id.Email_of_Worker);
        bio=findViewById(R.id.bio_of_Worker);
        address=findViewById(R.id.Address_of_Worker);
        Bookmark=findViewById(R.id.Bookmark_Image);
        AddToCart=findViewById(R.id.AddToCart);
        toolbar=findViewById(R.id.Toolbar_complete_view_Of_Product);
        AddToCart.setOnClickListener(this);
        Bookmark.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void gettingDataFromIntent()
    {
        Intent intent=getIntent();
        ProductLink=intent.getStringExtra("ProductLink");
        D_ProductCategoryByGender=intent.getStringExtra("ProductCategoryByGender");
        D_catoegory=intent.getStringExtra("ProductCategoryByMaterial");
        D_name=intent.getStringExtra("Workername");
        D_bio=intent.getStringExtra("Workerbio");
        D_email=intent.getStringExtra("WorkerEmail");
        D_Image=intent.getStringExtra("ImageLocation");
        D_address=intent.getStringExtra("Workeraddress");
       //D_mobile=Integer.parseInt(intent.getExtras().getString("Workermobile","0"));
        D_mobile= intent.getStringExtra("Workermobile");

    }
    private void settingDataIntoViews()
    {
        Glide.with(getApplicationContext()).load(D_Image).into(ProductImage);
        name.setText(D_name);
        email.setText(D_email);
        mobno.setText(D_mobile);
        address.setText(D_address);
        bio.setText(D_bio);
        worker_catogeory.setText(D_catoegory);
    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.AddToCart)
        {
            if (isInCart)
            {
                setAddToCart(null);
                isInCart=false;
            }
            else
            {
                D_Bookmarkshoe d_bookmarkshoe=new D_Bookmarkshoe(ProductLink,D_ProductCategoryByGender,D_catoegory);
                setAddToCart(d_bookmarkshoe);
                isInCart=true;
            }
        }

        else if (v.getId()==R.id.Bookmark_Image)
        {
            if (isBookMarked)
            {
                setBookmark(null);
                isBookMarked=false;
            }
            else
            {
                D_Bookmarkshoe d_bookmarkshoe=new D_Bookmarkshoe(ProductLink,D_ProductCategoryByGender,D_catoegory);
                setBookmark(d_bookmarkshoe);
                isBookMarked=true;
            }
        }
        else if (v.getId()==R.id.BuyNow)
        {
            BuyNowPage();
        }
        else if (v.getId()==R.id.Share)
        {
            Intent shareintent=new Intent(Intent.ACTION_SEND);
            shareintent.setType("text/plain");
            shareintent.putExtra(Intent.EXTRA_TEXT,"Book The Best "+D_catoegory+" name="+D_name+" In SitiServices");
            shareintent.putExtra(Intent.EXTRA_SUBJECT,"Download this  app");
            startActivity(Intent.createChooser(shareintent,"choose one among this to share"));
        }
    }





    private boolean checkAddressChecked()
    {
        if (!TextUtils.isEmpty(D_address_For_Shipping))
            return true;
        else
        {
            if (addressArrayList.size()!=0) {
                Snackbar.make(linearLayout, "Please Choose Address", Snackbar.LENGTH_LONG).show();
                return false;
            }
            else
            {
                Snackbar.make(linearLayout,"Please Add new Address",Snackbar.LENGTH_SHORT).show();
                return false;
            }
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if (requestCode==2)
        {
            Log.d("CompleteViewOfProduct", "onActivityResult: requestCode: " + requestCode);
            Log.d("CompleteViewOfProduct", "onActivityResult: resultCode: " + resultCode);
            //txnId=UPI20b6226edaef4c139ed7cc38710095a3&responseCode=00&ApprovalRefNo=null&Status=SUCCESS&txnRef=undefined
            //txnId=UPI608f070ee644467aa78d1ccf5c9ce39b&responseCode=ZM&ApprovalRefNo=null&Status=FAILURE&txnRef=undefined

            if (data != null) {
                Log.d("CompleteViewOfProduct", "onActivityResult: data: " + data.getStringExtra("response"));
                String res = data.getStringExtra("response");
                String search = "SUCCESS";
                if (res.toLowerCase().contains(search.toLowerCase())) {
                    Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
                    Log.e("CompleteViewOfProduct","Payment Successful");
                    D_PastOrders dPastOrders=new D_PastOrders(D_CurrentUser.getName(),D_CurrentUser.getPhone(),D_CurrentUser.getEmail(),D_address_For_Shipping,D_name,D_email,D_Image,""+D_address,""+D_mobile,D_catoegory,D_Date,amount,D_OrderId,D_Date+"_"+"Pending");
                    new AsyncTaskToStorePurchasedProduct().execute(dPastOrders);
                } else {
                    Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
                    Log.e("CompleteViewOfProduct","Payment failed");

                }

                snackbarHelp();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1)
            snackbarHelp();
    }


    private void snackbarHelp()
    {
        Snackbar.make(linearLayout,"Need Help! Please feel free to",Snackbar.LENGTH_LONG).setAction("Contact Us!", new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
                }
                else
                {
                    String number="+919177919976";
                    Intent intent4=new Intent(Intent.ACTION_CALL);
                    intent4.setData(Uri.parse("tel:"+number));
                    startActivity(intent4);
                }
            }
        }).show();
    }

    private void setListViewForAddress()
    {

        listView=findViewById(R.id.ListView_Address);
        adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_single_choice,addressArrayList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                D_address_For_Shipping=addressArrayList.get(position);
            }
        });
    }


    private void BuyNowPage() {
        if (checkAddressChecked())
        {

            Toast.makeText(this, "Booked", Toast.LENGTH_SHORT).show();
            //int p=Integer.parseInt(D_Price)*D_Quantity;
            //amount=""+p;
            String fakeamount="50";//min amount
            Uri uri = Uri.parse("upi://pay?pa="+payeeAddress+"&pn="+payeeName+"&tn="+transactionNote+
                    "&am="+fakeamount+"&cu="+currencyUnit);
            Log.d("CompleteViewOfProduct", "onClick: uri: "+uri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivityForResult(intent,2);
        }

    }

    private void setBookmark(D_Bookmarkshoe dBookmarkshoe)
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MyWishList");
        if (isBookMarked && !TextUtils.isEmpty(CurrentBookMarkedId))
        {
            databaseReference.child(CurrentBookMarkedId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                        Bookmark.setImageResource(R.drawable.ic_wishlist);
                    else
                        Bookmark.setImageResource(R.drawable.ic_wishlist_full);
                }
            });
        }
        else
        {
            CurrentBookMarkedId=ProductLink;
            databaseReference.child(CurrentBookMarkedId).setValue(dBookmarkshoe).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                        Bookmark.setImageResource(R.drawable.ic_wishlist_full);
                    else
                        Bookmark.setImageResource(R.drawable.ic_wishlist);
                }
            });
        }
    }

    public void TriggerNotifications() {
        Intent intent=new Intent(this, A_MyOrders.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),CHANNEL_1_ID)
                .setContentTitle("Payment")
                .setContentText("You have Booked "+D_name+" You will soon get the work finished")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Payment is Success"))
                .setChannelId(CHANNEL_1_ID)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_payment_success);
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0,builder.build());
    }

    public void createNotifications()
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel(CHANNEL_1_ID,"Channel1Booking",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Your Order has been Confirmed Successfully");
            channel.setShowBadge(true);
            NotificationManager manager=(NotificationManager) getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    class AsyncTaskToStorePurchasedProduct extends AsyncTask<D_PastOrders,Void,Void>
    {

        @Override
        protected Void doInBackground(D_PastOrders... d_pastOrders) {
            D_PastOrders d_pastOrders1=d_pastOrders[0];
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users").child("UsersNormalShoeOrders");
            final String key=databaseReference.push().getKey();
            databaseReference.child(key).setValue(d_pastOrders1).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.e("CompleteViewOfProduct","Purchased order details stored successfully");

                        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Users");
                        databaseReference1.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MyOrders").push().setValue(key);
                        TriggerNotifications();
                    }
                    else
                    {
                        Log.e("CompleteViewOfProduct","Unable to store purchased order details in firebase");
                        isStored=false;
                    }
                }
            });

            return null;
        }

    }

    private void getDataOfAddress()
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Addresses");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        D_Address dAddress=dataSnapshot1.getValue(D_Address.class);
                        addressArrayList.add(dAddress.toString());
                    }
                }
                else
                {
                    Log.e("CompleteViewOfProduct","Empty Snapshot of Address");
                    Snackbar.make(linearLayout,"Please Add Address By Going to Profile Section",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter.notifyDataSetChanged();
    }

    private void checkAlreadyBookMarked()
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.child("MyWishList").child(ProductLink).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    isBookMarked=true;
                    CurrentBookMarkedId=ProductLink;
                    Bookmark.setImageResource(R.drawable.ic_assignment_ind_black_24dp);
                }
                else
                {
                    isBookMarked=false;
                    Bookmark.setImageResource(R.drawable.ic_assignment_ind_black_24dp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void checkAlreadyInCart()
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.child("MyCart").child(ProductLink).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    isInCart=true;
                    CurrentInCartId=ProductLink;
                    AddToCart.setText("In Cart");
                }
                else
                {
                    isInCart=false;
                    AddToCart.setText("Add To Cart");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setAddToCart(D_Bookmarkshoe dBookmarkshoe)
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("MyCart");
        if (isInCart && !TextUtils.isEmpty(CurrentInCartId))
        {
            databaseReference.child(CurrentInCartId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                        AddToCart.setText("In Cart");
                    else
                        AddToCart.setText("Add To Cart");
                }
            });
        }
        else
        {
            CurrentInCartId=ProductLink;
            databaseReference.child(CurrentInCartId).setValue(dBookmarkshoe).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                        AddToCart.setText("In Cart");
                    else
                        AddToCart.setText("Add To Cart");
                }
            });
        }
    }
}
