package com.example.listviewandalertdialogwithsql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Landmarks> landmarks;
    private ArrayList<String> landmarks2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loaddata();
        mydb = new DatabaseHelper(this);
        Cursor res = mydb.getAllData();
        if (res.getCount() == 0) {
            return;
        } else {
            loadDatabase();
        }


    }

    public void loadDatabase() {
        recyclerView = findViewById(R.id.List);
        mydb = new DatabaseHelper(this);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        landmarks = new ArrayList<Landmarks>();
        Cursor res = mydb.getAllData();
        if (res.getCount() == 0) {
            landmarks.clear();
        }else{
        while (res.moveToNext()) {
            landmarks.add(new Landmarks(res.getString(1), "Add Details", res.getString(1)));
        }}

        myAdapter = new LandmarksAdapter(this, landmarks);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void SingleAlgo(final CharSequence[] sequences) {
        mydb = new DatabaseHelper(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Landmarks");
        builder.setItems(sequences, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Boolean inserted = mydb.insertData(sequences[which].toString().trim());
                if (inserted) {
                    loadDatabase();
                    landmarks2.remove(sequences[which]);
                    saveData();
                    Toast.makeText(MainActivity.this, "Successfully Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNeutralButton("Select Multiple Items", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MultipleAlgo(sequences);
            }
        });
        builder.create();
        builder.show();
    }

    public void MultipleAlgo(final CharSequence[] sequences) {
        final ArrayList selectedItem = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Landmarks");
        builder.setMultiChoiceItems(sequences, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked == true) {
                    if (!selectedItem.contains(sequences[which])) {
                        selectedItem.add(sequences[which]);
                        landmarks2.remove(sequences[which]);
                    } else {
                        selectedItem.remove(sequences[which]);
                    }
                } else if (isChecked == false) {
                    selectedItem.remove(sequences[which]);
                }
            }
        });
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < selectedItem.size(); i++) {
                    String task = selectedItem.get(i).toString().trim();
                    Boolean inserted = mydb.insertData(task);
                    if (inserted) {
                        loadDatabase();
                        saveData();
                        Toast.makeText(MainActivity.this, "Successfully Inserted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNeutralButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SingleAlgo(sequences);
            }
        });

        builder.create();
        builder.show();
    }

    public void ButtonOnclickDelete(View view) {
        mydb=new DatabaseHelper(this);
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.TextViewTitle);
        String task = String.valueOf(taskTextView.getText());
        landmarks2.add(task);
        int deleterow=mydb.DeleteData(task);
        if(deleterow>0){
            Toast.makeText(MainActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
            loadDatabase();
            saveData();
        }else{
            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
        }

    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int count = 0;
        final String[] sequences = new String[landmarks2.size()];
        if(landmarks2.isEmpty()){
            Toast.makeText(this, "Empty Choices", Toast.LENGTH_SHORT).show();
        }
        for (String temp : landmarks2) {
            System.out.println(temp);
            sequences[count] = temp;
            count++;
        }
        final String[] dd = {"Rizal", "National Museum of History", "National Museum of Arts"};
        switch (item.getItemId()) {
            case R.id.AddAction:
                SingleAlgo(sequences);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(landmarks2);
        editor.putString("landmarks", json);
        editor.apply();

    }

    public void loaddata() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("landmarks", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        landmarks2 = gson.fromJson(json, type);
        if (landmarks2==null) {
            landmarks2 = new ArrayList<String>();
            landmarks2.add("Rizal");
            landmarks2.add("National Museum of History");
            landmarks2.add("National Museum of Arts");
        }
    }
}
