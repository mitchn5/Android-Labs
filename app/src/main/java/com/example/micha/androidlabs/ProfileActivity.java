package com.example.micha.androidlabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    ImageButton picButton;
    Button chatButton;
    EditText emailText;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.e(ACTIVITY_NAME, "In function:" + "onStart");

        intent = getIntent();
        emailText = findViewById(R.id.profileEmail);
        emailText.setText(intent.getStringExtra("ReserveName"));
        picButton = findViewById(R.id.userPicIn);
        picButton.setOnClickListener((View v) -> {
            dispatchTakePictureIntent();
        });
        chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener((View v) ->
        {
            Intent intent = new Intent(this, ChatRoomActivity.class);
            startActivity(intent);
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

        Log.e(ACTIVITY_NAME, "In function:" + "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();

        Log.e(ACTIVITY_NAME, "In function:" + "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();

        Log.e(ACTIVITY_NAME, "In function:" + "onPause");
    }@Override
    protected void onStop() {
        super.onStop();

        Log.e(ACTIVITY_NAME, "In function:" + "onStop");
    }@Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e(ACTIVITY_NAME, "In function:" + "onDestroy");
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(ACTIVITY_NAME, "In function:" + "onActivityResult");

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picButton.setImageBitmap(imageBitmap);
        }
    }

}
