package com.example.micha.androidlabs;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity{

    public static final String ACTIVITY_NAME = "CHAT_ROOM_ACTIVITY";

    Button receive, send;
    ListView messages;
    EditText chatEdit;
    ArrayList<Message> texts;
    SQLiteDatabase db;

    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatEdit = findViewById(R.id.chatEdit);
        messages = findViewById(R.id.messages);
        texts = new ArrayList<>();
        adapter = new MessageAdapter(texts, ChatRoomActivity.this);

        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

        String [] columns = {MyOpener.COL_ID, MyOpener.COL_TEXT};
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        printCursor(results);
        results.moveToFirst();

        while(!(results.isAfterLast())) {
            String t = results.getString(results.getColumnIndex("TEXT"));
            long i = results.getLong(results.getColumnIndex("_id"));
            int iS = results.getInt(results.getColumnIndex("_id"));

            if (iS == 1) {
                texts.add(new Message(t, i, true, false));
            }
            else {
                texts.add(new Message(t, i, false, true));
            }

            results.moveToNext();
        }

        int textColIndex = results.getColumnIndex(MyOpener.COL_TEXT);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        results.moveToPrevious();
        while(results.moveToNext())
        {
            String name = results.getString(textColIndex);
            long id = results.getLong(idColIndex);
        }

        receive = findViewById(R.id.receiveButton);
        receive.setOnClickListener((View v) -> {
            addMessage("r", chatEdit.getText().toString());
            chatEdit.setText("");
            adapter.notifyDataSetChanged();
        });

        send = findViewById(R.id.sendButton);
        send.setOnClickListener((View v) -> {
            addMessage("s", chatEdit.getText().toString());
            chatEdit.setText("");
            adapter.notifyDataSetChanged();
        });

        messages.setAdapter(adapter);
    }

    public void addMessage(String type, String text) {
        ContentValues newRowValues = new ContentValues();
        newRowValues.put(MyOpener.COL_TYPE, type);
        newRowValues.put(MyOpener.COL_TEXT, text);

        long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);
        if (type.equals("s"))
            texts.add(new Message(text, newId, true, false));
        else
            texts.add(new Message(text, newId, false, true));
    }

    public void printCursor(Cursor c) {
        c.moveToFirst();
        int dbVer = db.getVersion();
        int colCount = c.getColumnCount();
        String colNames = "";
        for(int i=0; i<colCount; i++) {
            colNames += c.getColumnName(i) + ", ";
        }

        int messageIndex = c.getColumnIndex(MyOpener.COL_TEXT);
        int sentIndex = c.getColumnIndex(MyOpener.COL_TYPE);
        int idIndex = c.getColumnIndex(MyOpener.COL_ID);

        String text = dbVer + ", " + colCount + ", " + colNames +  c.getCount() + ", ";
        c.moveToFirst();
        while(!(c.isAfterLast())) {
            text += "" + c.getInt(idIndex)
                    + ", " + c.getString(messageIndex);
                    //+ ", " + c.getString(sentIndex);
            c.moveToNext();
        }

        Log.d(ACTIVITY_NAME, text);
    }
}
