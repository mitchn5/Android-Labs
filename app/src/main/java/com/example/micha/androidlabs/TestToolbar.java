package com.example.micha.androidlabs;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {
    Toolbar tb;
    String toastText = "This is the initial message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menulab6, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem1:
                Toast.makeText(TestToolbar.this, toastText, Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuItem2:
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.menu_text_edit, null);
                builder.setView(v)
                    .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            EditText menuEdit = v.findViewById(R.id.menuEdit);
                            toastText = menuEdit.getText().toString();
                        }
                    })
                    .setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                builder.show();
                break;
            case R.id.menuItem3:
                String text = "Go Back?";
                CharSequence cs = text;
                //Snackbar.make(tb.this, cs, Snackbar.LENGTH_SHORT);
                Snackbar sb = Snackbar.make(tb, cs, Snackbar.LENGTH_LONG)
                        .setAction("yes", e -> finish());
                        sb.show();
                break;
            case R.id.menuItem4:
                Toast.makeText(TestToolbar.this, "You clicked on the overflow item my guy", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }
}
