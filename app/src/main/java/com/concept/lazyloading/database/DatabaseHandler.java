package com.concept.lazyloading.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "LAZY_LOADING";
    private static final String LAZY = "LAZY_DATA";
    private static final int DB_VERSION = 4;
    private static final String ID = "ID";
    private static final String USERNAME = "NAME";
    private static final String AGE = "AGE";
    private static final String MOVIE_NAME = "MOVIE_NAME";
    private static final String GENRE = "GENRE";
    private static final String LIKE_ANIME = "ANIME";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + LAZY + "(" + USERNAME + " TEXT, " + AGE + " TEXT, "
                + MOVIE_NAME + " TEXT, " + GENRE + " TEXT, " + LIKE_ANIME + " TEXT" + ")" ;

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        String query = "DROP TABLE IF EXISTS " + LAZY;
//        sqLiteDatabase.execSQL(query);



//        ContentValues contentValues = new ContentValues();
//        String selectQuery = "SELECT * FROM " + LAZY;
//        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
//        int temp = 1;
//        if (cursor.moveToFirst()) {
//            do {
//                contentValues.put(ID, String.valueOf(temp));
//                sqLiteDatabase.update(LAZY, contentValues, ID + "=?", new String[] {""});
//                temp += 1;
//            }
//            while (cursor.moveToNext());
//        }
    }

    public boolean insertData(Data data) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, data.getUsername());
        contentValues.put(AGE, data.getAge());
        contentValues.put(MOVIE_NAME, data.getMovie_name());
        contentValues.put(GENRE, data.getGenre());
        contentValues.put(LIKE_ANIME, data.getLike_anime());

        sqLiteDatabase.insert(LAZY, null, contentValues);
        sqLiteDatabase.close();

        return true;
    }

    public List<Data> getAllData() {
        List<Data> userList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + LAZY;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Data userData = new Data();
                userData.setId(Integer.parseInt(cursor.getString(0)));
                userData.setUsername(cursor.getString(1));
                userData.setAge(cursor.getString(2));
                userData.setMovie_name(cursor.getString(3));
                userData.setGenre(cursor.getString(4));
                userData.setLike_anime(cursor.getString(5));
                userList.add(userData);
            }
            while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        cursor.close();
        return userList;
    }

    public boolean delete(String username) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(LAZY, USERNAME + "=?", new String[]{username});
        sqLiteDatabase.close();
        return true;
    }

    public List<Data> getLimitedData(int limit, int position) {
        List<Data> dataList = new ArrayList<>();

        String query = "SELECT " + USERNAME + ", " + AGE + ", " + MOVIE_NAME + ", " + GENRE + ", " + LIKE_ANIME + " FROM " + LAZY + " LIMIT " + position + ", " + limit ;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToNext()) {
            do {
                Data userData = new Data();
                userData.setId(Integer.parseInt(cursor.getString(0)));
                userData.setUsername(cursor.getString(1));
                userData.setAge(cursor.getString(2));
                userData.setMovie_name(cursor.getString(3));
                userData.setGenre(cursor.getString(4));
                userData.setLike_anime(cursor.getString(5));
                dataList.add(userData);
            }
            while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        cursor.close();
        return dataList;
    }

    public List<Data> getSingleData() {
        List<Data> dataList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.query(LAZY, new String[] {USERNAME,AGE, MOVIE_NAME, GENRE, LIKE_ANIME},null,null , null, null, null, String.valueOf(1));
        if (cursor != null)
            cursor.moveToNext();

        Data data = new Data();
        assert cursor != null;
        data.setUsername(cursor.getString(0));
        data.setAge(cursor.getString(1));
        data.setMovie_name(cursor.getString(2));
        data.setGenre(cursor.getString(3));
        data.setLike_anime(cursor.getString(4));
        dataList.add(data);

        sqLiteDatabase.close();
        return dataList;
    }

    public int getUserCounts() {
        String countQuery = "SELECT * FROM " + LAZY;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        cursor.close();
        sqLiteDatabase.close();
        return cursor.getCount();
    }

}
