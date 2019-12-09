package com.example.android.sitiservices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Chatbot extends AppCompatActivity {

    ListView listView;
    FloatingActionButton btnsend1;
    EditText editText;
    ImageView imageView;

    private Bot bot;
    private static Chat chat;
    private chatmessageadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot2);
        listView=findViewById(R.id.listview);
        btnsend1=findViewById(R.id.btnsend);
        editText=findViewById(R.id.editText);
        imageView=findViewById(R.id.imageview);

        adapter=new chatmessageadapter(this,new ArrayList<chatmessage>());
        listView.setAdapter(adapter);
        btnsend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=editText.getText().toString();
                String response=chat.multisentenceRespond(editText.getText().toString());
                if(TextUtils.isEmpty(message))
                {
                    Toast.makeText(getApplicationContext(),"please enter a query..",Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMessage(message);
                botsReply(response);

                //clear edittext
                editText.setText("");
                listView.setSelection(adapter.getCount() - 1);
            }
        });

        boolean isavailable=isSDCartAvailable();
        AssetManager assets=getResources().getAssets();
        File filename=new File(Environment.getExternalStorageDirectory().toString() +  "/TBC/bots/sitiservicesbot");
        boolean makefile=filename.mkdirs();
        if(filename.exists())
        {
            //read the line
            try {

                {
                    for(String dir:assets.list("sitiservicesbot"))
                    {
                        File subDir=new File(filename.getPath()+"/"+dir);
                        boolean subDir_check=subDir.mkdirs();

                        for(String file:assets.list("sitiservicesbot/" + dir))
                        {
                            File newFile=new File(filename.getPath()+ "/" +dir+ "/" +file);

                            if(newFile.exists())
                            {
                                continue;
                            }

                            InputStream in;
                            OutputStream out;
                            String str;
                            in=assets.open("sitiservicesbot/"+dir+ "/" +file);
                            out=new FileOutputStream(filename.getPath()+ "/" +dir+ "/" +file);

                            //copy files from assets to mobiles sd card
                            copyFile(in,out);
                            in.close();
                            out.flush();
                            out.close();
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //get the working directory
        MagicStrings.root_path=Environment.getExternalStorageDirectory().toString() + "/TBC";
        AIMLProcessor.extension=new PCAIMLProcessorExtension();
        bot =new Bot("sitiservicesbot",MagicStrings.root_path,"chat");
        chat=new Chat(bot);
    }



    private void copyFile(InputStream in, OutputStream out) throws IOException {

        byte[] buffer=new byte[1024];
        int read;
        while((read=in.read(buffer))!=-1)
        {
            out.write(buffer,0,read);
        }
    }
    public static boolean isSDCartAvailable() {

        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)? true :false;

    }

    private void botsReply(String response)
    {
        chatmessage chatmessage1=new chatmessage(false,false,response);
        adapter.add(chatmessage1);
    }


    private void sendMessage(String message)

    {
        chatmessage chatmessage2=new chatmessage(false,true,message);
        adapter.add(chatmessage2);
    }

}

