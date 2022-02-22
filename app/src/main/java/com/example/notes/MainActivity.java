package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.notes.Adapter.NotesAdapter;
import com.example.notes.Model.NotesModel;
import com.example.notes.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    String mail,username;
    DBHelper DB;
    ArrayList<NotesModel> list=new ArrayList<>();
    NotesAdapter noteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SharedPreferences shrd=getSharedPreferences("user",MODE_PRIVATE);

        getSupportActionBar().setElevation(10f);
        DB=new DBHelper(MainActivity.this);
        Intent i=getIntent();
        mail=shrd.getString("STR","");
        username=DB.getUsername(mail);
        getSupportActionBar().setTitle(username+"'s Notes");
        list=DB.getAllData(username);

        binding.NotesRecycleView.setHasFixedSize(true);
        noteAdapter=new NotesAdapter(list,this,username);
        binding.NotesRecycleView.setAdapter(noteAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        binding.NotesRecycleView.setLayoutManager(staggeredGridLayoutManager);
        binding.createNoteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CreateNoteActivity.class);
                intent.putExtra("mail",mail);
                intent.putExtra("userName",username);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logOut:
                SharedPreferences clear=getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor editor=clear.edit();
                editor.putString("STR",null);
                editor.apply();
                finish();
                startActivity(new Intent(MainActivity.this,LogInActivity.class));
                break;
        }

        return true;
    }


}