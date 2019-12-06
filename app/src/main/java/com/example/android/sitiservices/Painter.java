package com.example.android.sitiservices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Painter extends AppCompatActivity    implements
        AdapterView.OnItemSelectedListener {
    String[] city = { "Hyderabad", "Bangalore", "Chennai", "Mumbai", "Delhi"};
    String[] category = { "Installation", "Repair"};
    EditText edname,eddob,edmail,edphone,edadress;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painter);
        Spinner spin1 = (Spinner) findViewById(R.id.s1);
        Spinner spin2 = (Spinner) findViewById(R.id.s2);
        button=findViewById(R.id.b1);
        edname=findViewById(R.id.e1);
        eddob=findViewById(R.id.e2);
        edmail=findViewById(R.id.e3);
        edphone=findViewById(R.id.e4);
        edadress=findViewById(R.id.e5);
        eddob.addTextChangedListener(bookTextWatcher);
        edmail.addTextChangedListener(bookTextWatcher);
        edphone.addTextChangedListener(bookTextWatcher);
        edadress.addTextChangedListener(bookTextWatcher);
        edname.addTextChangedListener(bookTextWatcher);

        spin1.setOnItemSelectedListener(this);
        spin2.setOnItemSelectedListener(this);
        ArrayAdapter a1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,city);
        a1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin1.setPrompt("Please Select");
        spin1.setAdapter(a1);
        ArrayAdapter a2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,category);
        a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin2.setAdapter(a2);


    }
    private TextWatcher bookTextWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String nameInput=edname.getText().toString().trim();
            String dobInput=eddob.getText().toString().trim();
            String mailInput=edmail.getText().toString().trim();
            String phoneInput=edphone.getText().toString().trim();
            String addressInput=edadress.getText().toString().trim();
            button.setEnabled(!nameInput.isEmpty() && !dobInput.isEmpty() && !mailInput.isEmpty() && !phoneInput.isEmpty() && !addressInput.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    public void onItemSelected(AdapterView<?> spinner, View arg1, int position, long arg3) {
        int id=spinner.getId();
        switch (id)
        {

            case R.id.s1:

            case R.id.s2:
                

        }
        }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void Booking(View view) {



        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        Intent intent = new Intent(this,Rating.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("SitiService");
        builder.setContentText("Your booking has been conformed");
        builder.setSubText("Rate us!...");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(1, builder.build());

    }
}
