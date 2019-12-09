package com.example.android.sitiservices.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sitiservices.Login.A_SignIn;
import com.example.android.sitiservices.Login.D_CurrentUser;
import com.example.android.sitiservices.R;
import com.example.android.sitiservices.Utils.CommonClassForSharedPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileSection extends AppCompatActivity implements View.OnClickListener {
    ImageView profileImage,EditProfile;
    ImageView signout;
    TextView signout2;
    TextView viewalladdress;
    TextView profileName;
    TextView profilePhoneNumber;
    TextView AccountSettings;
    TextView Myorders;
    TextView WishList;
    TextView MyCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_section);
        findViewByIds();
        setProfileImage();
        setProfileName();
        setProfilePhoneNumber();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            Intent intent=new Intent(getApplicationContext(), A_SignIn.class);
            intent.putExtra("Number",2);
            startActivity(intent);
        }
    }



    private void findViewByIds()
    {
        profileImage =findViewById(R.id.Profile_Fragment_ImgBtn_Profile);
        signout=findViewById(R.id.Profile_Fragment_ImgView_Logout_Icon);
        signout2=findViewById(R.id.Profile_Fragment_TxtView_Logout_Icon);
        viewalladdress=findViewById(R.id.Profile_Fragment_TxtView_Profile_MyAddresses);
        profileName=findViewById(R.id.Profile_Fragment_TxtView_Profile_Name);
        profilePhoneNumber=findViewById(R.id.Profile_Fragment_TxtView_Profile_Phone);
        AccountSettings=findViewById(R.id.Profile_Fragment_TxtView_Profile_MySettings);
        Myorders=findViewById(R.id.Profile_Fragment_TxtView_Profile_MyOrders);
        WishList=findViewById(R.id.Profile_Fragment_TxtView_Profile_MyWishList);
        MyCart=findViewById(R.id.Profile_Fragment_TxtView_Profile_MyCart);
        EditProfile=findViewById(R.id.Profile_Fragment_ImgBtn_Profile_Edit_Btn);

        EditProfile.setOnClickListener(this);
        AccountSettings.setOnClickListener(this);
        viewalladdress.setOnClickListener(this);
        signout2.setOnClickListener(this);
        signout.setOnClickListener(this);
        profileImage.setOnClickListener(this);
        Myorders.setOnClickListener(this);
        WishList.setOnClickListener(this);
        MyCart.setOnClickListener(this);
    }
    private void setProfileImage()
    {
        if (D_CurrentUser.Gender==null)
            profileImage.setImageResource(R.drawable.ic_account_circle_black_24dp);
        else if (D_CurrentUser.Gender.contentEquals("Male"))
            profileImage.setImageResource(R.drawable.ic_account_circle_black_24dp);
        else
            profileImage.setImageResource(R.drawable.ic_account_circle_black_24dp);
    }
    private void setProfileName()
    {
        profileName.setText(D_CurrentUser.getName());
    }
    private void setProfilePhoneNumber(){
        profilePhoneNumber.setText(D_CurrentUser.getPhone());}


    public void onClick(View v) {

        if (v.getId()==R.id.Profile_Fragment_ImgBtn_Profile_Edit_Btn)
        {
            changeProfile();
        }
        else if (v.getId()==R.id.Profile_Fragment_ImgView_Logout_Icon || v.getId()==R.id.Profile_Fragment_TxtView_Logout_Icon)
        {
            FirebaseAuth.getInstance().signOut();
            clearCurrentUserData();
            CommonClassForSharedPreferences.setDataIntoSharedPreference(getApplicationContext());
            Intent intent=new Intent(getApplicationContext(),A_SignIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if (v.getId()==R.id.Profile_Fragment_TxtView_Profile_MyAddresses)
        {
            startActivity(new Intent(getApplicationContext(), A_MyAddresses.class));
        }
        else if (v.getId()==R.id.Profile_Fragment_TxtView_Profile_MySettings)
        {
            startActivity(new Intent(getApplicationContext(),A_AccountSettings.class));
        }
        else if (v.getId()==R.id.Profile_Fragment_TxtView_Profile_MyOrders)
        {
            startActivity(new Intent(getApplicationContext(), A_MyOrders.class));
        }
        else if (v.getId()==R.id.Profile_Fragment_TxtView_Profile_MyWishList)
        {
            startActivity(new Intent(getApplicationContext(),A_WishList.class));
        }
        else if (v.getId()==R.id.Profile_Fragment_TxtView_Profile_MyCart)
        {
            startActivity(new Intent(getApplicationContext(),A_MyCart.class));
        }

    }

    private void changeProfile() {
        final TextInputLayout textInputLayoutForName=new TextInputLayout(getApplicationContext());
        textInputLayoutForName.setHint("Name");
        textInputLayoutForName.setErrorEnabled(true);
        final TextInputLayout textInputLayoutForPhone=new TextInputLayout(getApplicationContext());
        textInputLayoutForPhone.setErrorEnabled(true);
        textInputLayoutForPhone.setHint("Phone");

        final TextInputEditText textInputEditTextForName=new TextInputEditText(getApplicationContext());
        final TextInputEditText textInputEditTextForPhone=new TextInputEditText(getApplicationContext());
        textInputLayoutForName.addView(textInputEditTextForName);
        textInputLayoutForPhone.addView(textInputEditTextForPhone);
        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(textInputLayoutForName);
        linearLayout.addView(textInputLayoutForPhone);
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(linearLayout).setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name=textInputEditTextForName.getEditableText().toString();
                String phone=textInputEditTextForPhone.getEditableText().toString();
                if (TextUtils.isEmpty(name))
                {
                    textInputLayoutForName.setError("Name Cannot be empty!");
                }
                else if (TextUtils.isEmpty(phone))
                {
                    textInputLayoutForPhone.setError("Phone cannot be empty!");
                }
                else
                {
                    if (name.contentEquals(D_CurrentUser.getName()) || phone.contentEquals(D_CurrentUser.getPhone()))
                    {
                        if (name.contentEquals(D_CurrentUser.getName()))
                            textInputLayoutForName.setError("Update with new Name");
                        else if (phone.contentEquals(D_CurrentUser.getPhone()))
                            textInputLayoutForPhone.setError("Update with new Phone number");
                    }
                    else
                    {
                        if (phone.length()!=10)
                            textInputLayoutForPhone.setError("Phone number not valid!");
                        else
                        {
                            textInputLayoutForName.setError(null);
                            textInputLayoutForPhone.setError(null);
                            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            databaseReference.child("Name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        Toast.makeText(getApplicationContext(), "Name Updated SuccessFully", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(getApplicationContext(), "Unable to update name", Toast.LENGTH_SHORT).show();
                                }
                            });
                            databaseReference.child("Phone").setValue(phone);
                            D_CurrentUser.setName(name);
                            D_CurrentUser.setPhone(phone);
                            setProfileName();
                            setProfilePhoneNumber();
                            CommonClassForSharedPreferences.setDataIntoSharedPreference(getApplicationContext());

                        }
                    }
                }
            }
        }).setTitle("Update Your details").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private void clearCurrentUserData()
    {
        D_CurrentUser.setGender(null);
        D_CurrentUser.setPhone(null);
        D_CurrentUser.setName(null);
        D_CurrentUser.setEmail(null);
    }

}
