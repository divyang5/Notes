package com.example.notes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.notes.Model.NotesModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class DBHelper extends SQLiteOpenHelper {

    String currentUser;
    public DBHelper
            (@Nullable Context context) {
        super(context, "DATABASE", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserDetails(userName TEXT primary key,contact TEXT ,email TEXT,password TEXT)");
        DB.execSQL("create Table NOTES(uid integer primary key AUTOINCREMENT ,userName TEXT  references UserDetails(userName) ON UPDATE SET NULL ON DELETE SET NULL , title TEXT   ,content TEXT,image BLOB)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists UserDetails");
        DB.execSQL("Drop Table if exists NOTES");

    }

    public boolean insertData(String userName,String title,String content){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("userName",userName);
        values.put("title",title);
        values.put("content",content);
        long result=DB.insert("NOTES",null,values);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertData(String userName, String title, String content, Bitmap image){
        byte[] data ;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        data=outputStream.toByteArray();
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("userName",userName);
        values.put("title",title);
        values.put("content",content);
        values.put("image",data);
        long result=DB.insert("NOTES",null,values);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public  boolean updateData(String uid,String title,String content){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("title",title);
        values.put("content",content);
        long result=db.update("NOTES",values,"uid=?",new String[]{uid});
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertUserData(String userName,String contact,String email,String password){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("userName",userName);
        values.put("contact",contact);
        values.put("email",email);
        values.put("password",password);

        long result=DB.insert("UserDetails",null,values);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    @SuppressLint("Range")
    public String getUsername(String mail){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from UserDetails",null);
//        Cursor cursor1=db.rawQuery("select * from UserDetails where contact=:"+mail+";",null);
        while (cursor.moveToNext()){
            String email=cursor.getString(cursor.getColumnIndex("email"));
            String contact=cursor.getString(cursor.getColumnIndex("contact"));
            if(email.equals(mail) || contact.equals(mail)) {
                Log.d("username is", cursor.getString(cursor.getColumnIndex("userName")));
                return cursor.getString(cursor.getColumnIndex("userName"));
            }
        }
        cursor.close();
        Log.d("username is"," nothing");
        return null;
    }
    public ArrayList<NotesModel> getAllData(String username) {
        ArrayList<NotesModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select  * from NOTES where userName=?", new String[]{username});
        if (cursor.moveToFirst()) {
            do {
                NotesModel note = new NotesModel();
                note.setTitle(cursor.getString(2));
                note.setContent(cursor.getString(3));
                //set the image
                list.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public boolean updateUserData(String userName,String contact,String email,String password){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("userName",userName);
        values.put("contact",contact);
        values.put("email",email);
        values.put("password",password);

        long result=DB.insert("UserDetails",null,values);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkUserName(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        Cursor cursor=db.rawQuery("select * from UserDetails where userName=?",new String[]{name});
        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }
    public boolean checkMail(String mail){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        Cursor cursor=db.rawQuery("select * from UserDetails where email=?",new String[]{mail});
        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }
    public boolean checkContact(String contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        Cursor cursor=db.rawQuery("select * from UserDetails where contact=?",new String[]{contact});
        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }
    public boolean checkUserNamePassword(String email,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from UserDetails where (email=? and password=?)",new String[]{email,password} );
        Cursor cursor2=db.rawQuery("select * from UserDetails where  (contact=? and password=?)",new String[]{email,password} );
        if(cursor.getCount()>0 || cursor2.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
    public  boolean checkPassword(String password){
        boolean b2= Pattern.compile("^([a-z](?=(.*?[A-Z]){2,})(?=(.*?[0-9]){2,})(?=.*?[#?!@$%^&*-])).{8,15}$").matcher(password).matches();
        if(b2){
            return true;
        }else {
            return false;
        }
    }
    public  boolean checkEmail(String email){
        boolean b2= Pattern.compile("^([A-Za-z0-9-_.]{4,25}+@[A-Za-z0-9-_]+(?:\\.[A-Za-z0-9]{4,25}+)+)",Pattern.CASE_INSENSITIVE).matcher(email).matches();
        if(b2){
            return true;
        }else {
            return false;
        }
    }
    public boolean checkIndianContact(String contact){
        boolean b2= Pattern.compile("^[+]+91+[0-9]{10}",Pattern.CASE_INSENSITIVE).matcher(contact).matches();
        if(b2){
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("Range")
    public String getUid(String userName, String title, String content){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from NOTES where (userName=? and title=? and content =?)",new String[]{userName,title,content});
        while (cursor.moveToNext()){
            return cursor.getString(cursor.getColumnIndex("uid"));
        }
        cursor.close();
        return null;
    }
    public boolean deleteNote(String uid){
        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.delete("NOTES","uid=?",new String[]{uid});
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

}
