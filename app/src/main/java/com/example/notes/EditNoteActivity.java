package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.notes.databinding.ActivityEditNoteBinding;

import java.util.Objects;

public class EditNoteActivity extends AppCompatActivity {

    ActivityEditNoteBinding binding;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DB=new DBHelper(EditNoteActivity.this);
        setSupportActionBar(binding.toolbarOfEditNote);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Intent data=getIntent();
        String username=data.getStringExtra("userName");
        Log.d(" USERNAME IS","the message is"+username);
        String uid=DB.getUid(username,data.getStringExtra("title"),data.getStringExtra("content"));
        binding.UpdateNoteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTittle=binding.TittleOfEditNote.getText().toString();
                String newDesc=binding.DescOfEditNote.getText().toString();
                if(newTittle.isEmpty()){
                    Toast.makeText(view.getContext(), "Tittle is empty", Toast.LENGTH_SHORT).show();
                    return;
                }else if(newDesc.isEmpty()) {
                    Toast.makeText(view.getContext(), "Note is empty", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Boolean updated=DB.updateData(uid,newTittle,newDesc);
                    if (updated){
                        Toast.makeText(EditNoteActivity.this, "updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(EditNoteActivity.this,MainActivity.class));
                    }else{
                        Toast.makeText(EditNoteActivity.this, "updated problem", Toast.LENGTH_SHORT).show();
                    }
                }
                //update the record
            }
        });
        binding.TittleOfEditNote.setText(data.getStringExtra("title"));
        binding.DescOfEditNote.setText(data.getStringExtra("content"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}