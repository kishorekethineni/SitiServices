package com.example.android.sitiservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

public class chatmessageadapter extends ArrayAdapter<chatmessage> {

    private static final int MY_MESSAGE=0;
    private static final int BOT_MESSAGE=1;
    public chatmessageadapter(Context context, List<chatmessage> data) {
        super(context, R.layout.user_query_layout,data);
    }



    @Override
    public int getItemViewType(int position) {

        chatmessage item=getItem(position);
        if(item.isIsmine() && !item.isIsimage())
        {
            return MY_MESSAGE;
        }
        else
        {
            return BOT_MESSAGE;
        }

    }
@NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        int viewtype=getItemViewType(position);
        if(viewtype==MY_MESSAGE)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_query_layout,parent,false);
            TextView textView=convertView.findViewById(R.id.text);
            textView.setText(getItem(position).getContent());
        }
       else if(viewtype==BOT_MESSAGE)
    {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.bots_reply_layout,parent,false);
        TextView textView=convertView.findViewById(R.id.text);
        textView.setText(getItem(position).getContent());
    }
       convertView.findViewById(R.id.chatMessageView).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(getContext(),"clicked..", Toast.LENGTH_SHORT).show();
           }
       });
       return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }
}
