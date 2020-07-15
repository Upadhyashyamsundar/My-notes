package com.shyam.mynotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteeditingActivity extends AppCompatActivity {

    int Noteid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noteediting);

        EditText editText=(EditText)findViewById(R.id.editText);

        Intent intent=getIntent();

        Noteid=intent.getIntExtra("Noteid",-1);

        if(Noteid!=-1){

             editText.setText(MainActivity.notes.get(Noteid));

        }else {

            MainActivity.notes.add("");
            Noteid=MainActivity.notes.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();

        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                MainActivity.notes.set(Noteid,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.shyam.mynotes", Context.MODE_PRIVATE);

                HashSet<String> set=new HashSet(MainActivity.notes);

                sharedPreferences.edit().putStringSet("Notes",set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
}
