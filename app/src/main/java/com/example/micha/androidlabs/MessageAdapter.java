package com.example.micha.androidlabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter {
    ArrayList<Message> texts;
    private Context context;

    public MessageAdapter(ArrayList<Message> m, Context c) {
        this.texts = m;
        this.context = c;

    }

    @Override
    public int getCount() {
        return texts.size();
    }

    @Override
    public Message getItem(int position) {
        return texts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View newView;
        TextView tv;

        if (texts.get(position).getIsReceived() == true) {
            newView = inflater.inflate(R.layout.receive_layout, parent, false);
            tv = newView.findViewById(R.id.messageReceive);
            tv.setText(getItem(position).getText());
        }
        else {
            newView = inflater.inflate(R.layout.send_layout, parent, false);
            tv = newView.findViewById(R.id.messageSend);
            tv.setText(getItem(position).getText());
        }

        return newView;
    }
}
