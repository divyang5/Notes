package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.notes.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {

    ActivityLogInBinding binding;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        db=new DBHelper(this);
        if(loadData()!=null){
            startActivity(new Intent(LogInActivity.this,MainActivity.class));
            finish();
        }
        binding.LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=binding.etLoginEmail.getText().toString().trim();
                String password=binding.etLoginPassword.getText().toString().trim();
                if(mail.isEmpty() ){
                    binding.etLoginEmail.setError(" Enter Email Or Contact ");
                    return;
                }else if(binding.etLoginPassword.getText().toString().isEmpty()){
                    binding.etLoginPassword.setError(" Enter password ");
                    return;

                }else{
                    Boolean checkMail=db.checkMail(mail);
                    Boolean checkContact=db.checkContact(mail);
                    Boolean checkUserPass=db.checkUserNamePassword(mail,password);
                    if(checkMail || checkContact) {
                        if (checkUserPass) {
                            Toast.makeText(LogInActivity.this, "SignIn successful", Toast.LENGTH_SHORT).show();
                            SharedPreferences shrd=getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor editor=shrd.edit();
                            editor.putString("STR",mail);
                            editor.apply();
                            finish();
                            Intent intent=new Intent(LogInActivity.this,MainActivity.class);
                            intent.putExtra("mail",mail);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LogInActivity.this, "Check user name and password", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        binding.etLoginEmail.setError("User not found");
                    }
                }
            }
        });
        binding.NewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signIn() {
        //create all sign in thing

    }
    public String loadData(){
        SharedPreferences getShrd=getSharedPreferences("user",MODE_PRIVATE);
        return getShrd.getString("STR",null);
    }
}