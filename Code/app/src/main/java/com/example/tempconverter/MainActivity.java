package com.example.tempconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Button btnLoad, btnSave;

    EditText txtID, txtContent, txtTitle, txtTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID = findViewById(R.id.txtID);
        txtContent = findViewById(R.id.txtContent);

        final ArrayList<String> array = new ArrayList<>();
        array.add("Note 1");


        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);




        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = txtID.getText().toString();
                SharedPreferences pref = getPreferences(0);

                String json = pref.getString(id, "");

                if (json.length() == 0) {
                    txtTitle.setText("ID not found. New node is created!");
                } else {
                    NoteModel note = NoteModel.fromJson(json);
                    txtContent.setText(note.content);
                    txtTitle.setText(note.title);
                    txtTags.setText(note.tags);
                    txtID.setText(note.id);
                }

            }
        });
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);

        final ListView listView = (ListView) findViewById(R.id.listviews);
        listView.setAdapter(adapter);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteModel note = new NoteModel();
                note.id = txtID.getText().toString();
                note.content = txtContent.getText().toString();

                SharedPreferences pref = getPreferences(0);
                SharedPreferences.Editor editor = pref.edit();

                String json = note.toJson();

                Log.i("json", json);

                editor.putString(note.id, json);
                editor.commit();
                array.add(note.id);
                adapter.notifyDataSetChanged();

            }
        });

    }
}
