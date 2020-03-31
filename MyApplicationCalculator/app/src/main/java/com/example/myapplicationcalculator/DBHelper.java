package com.example.myapplicationcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper {
    private static final String DATABASE_NAME  = "main";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "user2";
//    private static final String ID = "_id";
    private static final String USERNAME = "username2";//表中的第一列名
    private static final String PASSWORD = "password2";
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    public static class DBOpenHelper extends SQLiteOpenHelper {
            public static final String CREATE_TABLE = "create table "
                +DATABASE_NAME+"."+TABLE_NAME + "("
                +USERNAME + " INT PRIMARY KEY not null,"
                +PASSWORD + " not null);";
        public DBOpenHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_TABLE);
            System.out.println(13);
        }


        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("drop table if exists " + TABLE_NAME);
            onCreate(db);
        }

    }
    public DBHelper(Context context){
        helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    public Long insert(User user){
        ContentValues values = new ContentValues();
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD ,user.getPassword());
        Long i= db.insert(TABLE_NAME , null, values);//已存在返回-1
        System.out.println(i);
        return i;
    }

    public User query(String username){
        User user = new User();
        Cursor cursor = db.query(TABLE_NAME , new String[]{PASSWORD },//类似于ResultSet
                USERNAME+"=?",new String[]{username}, null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            user.setUsername(username);
            user.setPassword(cursor.getString(0));
            return user;
        }
        cursor.close();
        return new User("","");//不返回空对象,以防出现类型转换错误
    }

}
