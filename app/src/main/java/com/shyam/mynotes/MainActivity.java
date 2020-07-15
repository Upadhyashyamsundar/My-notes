package com.shyam.mynotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView listView;
   static ArrayList<String> notes=new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();

        menuInflater.inflate(R.menu.menu_men,menu);

        return   super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId()==R.id.addnotes)
        {
            Intent intent=new Intent(getApplicationContext(),NoteeditingActivity.class);
            startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.listview);

        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.shyam.mynotes", Context.MODE_PRIVATE);

        HashSet<String> set=(HashSet<String>)sharedPreferences.getStringSet("Notes",null);

        if(set==null) {

            notes.add("Example Text");
        }else{

            notes=new ArrayList(set);

        }

       arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                Intent intent=new Intent(getApplicationContext(),NoteeditingActivity.class);
                intent.putExtra("Noteid",position);
                startActivity(intent);


            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Delete this notes?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                notes.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.shyam.mynotes", Context.MODE_PRIVATE);

                                HashSet<String> set=new HashSet(MainActivity.notes);

                                sharedPreferences.edit().putStringSet("Notes",set).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();


                return true;
            }
        });


    }
}
