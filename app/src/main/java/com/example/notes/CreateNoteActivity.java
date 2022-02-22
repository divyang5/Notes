package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.notes.databinding.ActivityCreateNoteBinding;

public class CreateNoteActivity extends AppCompatActivity {

    ActivityCreateNoteBinding binding;
    DBHelper db;
    String mail,username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCreateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db=new DBHelper(this);
        Intent i=getIntent();
        mail=i.getStringExtra("mail");
        username=db.getUsername(mail);
        setSupportActionBar(binding.toolbarOfCreateNote);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.saveNoteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=binding.createNoteTittleOfNote.getText().toString();
                String desc=binding.crateDescOfNote.getText().toString();
                if(title.isEmpty()){
                    Toast.makeText(CreateNoteActivity.this, "Tittle is empty", Toast.LENGTH_SHORT).show();
                    return;
                }else if(desc.isEmpty()) {
                    Toast.makeText(CreateNoteActivity.this, "Note is empty", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Boolean insertData=db.insertData(username,title,desc);
                    if(insertData){
                        Toast.makeText(CreateNoteActivity.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent=new Intent(CreateNoteActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}