package com.example.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.notes.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    DBHelper db;
    AlertDialog alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        db=new DBHelper(this);
        binding.SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName=binding.etSignUpUserName.getText().toString().trim();
                String contact=binding.etSignUpContact.getText().toString().trim();
                String email=binding.etSignUpEmail.getText().toString().trim();
                String password=binding.etSignUpPassword.getText().toString().trim();

                if(email.isEmpty()){
                    binding.etSignUpEmail.setError(" Enter Email");
                    return;
                }else if(password.isEmpty()){
                    binding.etSignUpPassword.setError(" Enter Password");
                    return;
                }else if(contact.isEmpty()){
                    binding.etSignUpContact.setError(" Enter contact");
                    return;
                }else if(userName.isEmpty()){
                    binding.etSignUpUserName.setError(" Enter username ");
                    return;
                }else {
                    Boolean checkUser=db.checkUserName(userName);
                    Boolean checkContact=db.checkContact(contact);
                    if(!checkContact && db.checkIndianContact(contact)) {
                        if (!checkUser) {
                            Boolean checkEmail=db.checkEmail(email);
                            if(checkEmail) {
                                Boolean checkPassword = db.checkPassword(password);
                                if (checkPassword) {
                                    Boolean insert = db.insertUserData(userName, contact, email, password);
                                    if (insert) {
                                        Toast.makeText(SignUpActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Problem encounters", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    new AlertDialog.Builder(SignUpActivity.this)
                                            .setTitle("Password must be")
                                            .setMessage("min 8 and max 15 characters, should not contain your name, \n" +
                                                    "the first character should be lowercase, must contain at least 2 uppercase characters, \n" +
                                                    "2digits and 1 special character")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.cancel();
                                                }
                                            })
                                            .show();

                                    return;
                                }
                            }else {
                                binding.etSignUpEmail.setError("Email is Not Valid");
                                new AlertDialog.Builder(SignUpActivity.this)
                                        .setTitle("Email must be")
                                        .setMessage("name/domain min 4 character and max\n" +
                                                "25 characters long")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        })
                                        .show();
                                return;
                            }
                        } else {
                            binding.etSignUpUserName.setError("user already exist please sign in ");
//                            binding.etSignUpUserName.setTextColor(getResources().getColor(R.color.redr));
                            return;
                        }
                    }else {
                        if(checkContact){
                        binding.etSignUpContact.setError("This Contact Number already exists ");
//                        binding.etSignUpContact.setTextColor(getResources().getColor(R.color.redr));
                        return;
                        }else if(!db.checkIndianContact(contact)){
                            binding.etSignUpContact.setError("Number is not valid ");
                            new AlertDialog.Builder(SignUpActivity.this)
                                    .setTitle("Contact must be")
                                    .setMessage("start with +91xxxxxxxxxx")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    })
                                    .show();
                        }

                    }
                }
            }
        });
        binding.AlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpActivity.this,LogInActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_left);
            }
        });
    }
}