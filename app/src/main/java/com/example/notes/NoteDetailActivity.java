package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.notes.databinding.ActivityNoteDetailBinding;

public class NoteDetailActivity extends AppCompatActivity {
    ActivityNoteDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNoteDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarOfNoteDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent data=getIntent();

        binding.EditNoteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(),EditNoteActivity.class);
                i.putExtra("title",data.getStringExtra("title"));
                i.putExtra("content",data.getStringExtra("content"));
                i.putExtra("userName",data.getStringExtra("userName"));
                startActivity(i);
            }
        });
        binding.TittleOfNoteDetail.setText(data.getStringExtra("title"));
        binding.DescOfNoteDetail.setText(data.getStringExtra("content"));
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}