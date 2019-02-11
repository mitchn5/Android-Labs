package com.example.micha.androidlabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    Button receive, send;
    ListView messages;
    EditText chatEdit;
    ArrayList<Message> texts;

    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatEdit = findViewById(R.id.chatEdit);
        messages = findViewById(R.id.messages);
        texts = new ArrayList<>();
        adapter = new MessageAdapter(texts, ChatRoomActivity.this);


        receive = findViewById(R.id.receiveButton);
        receive.setOnClickListener((View v) -> {
            addMessage('r', chatEdit.getText().toString());
            chatEdit.setText("");
            adapter.notifyDataSetChanged();
        });

        send = findViewById(R.id.sendButton);
        send.setOnClickListener((View v) -> {
            addMessage('s', chatEdit.getText().toString());
            chatEdit.setText("");
            adapter.notifyDataSetChanged();
        });

        messages.setAdapter(adapter);
    }

    public void addMessage(char type, String text) {
        Message message = new Message(type, text);
        texts.add(message);
    }
}
