package me.twango.twango.helper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import org.json.JSONObject;

import me.twango.twango.entity.User;


public class DBHelper extends SQLiteOpenHelper{
	public static final String DATABASE_NAME = "FeedWLAndroid.db";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 2);
		//	  tableName = TableName;

	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		String query;
		query = "CREATE TABLE IF NOT EXISTS user(loginType TEXT,name TEXT,email TEXT,bitmap BLOB,imageUrl TEXT)";
		database.execSQL(query);
	}
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {

		onCreate(database);
	}

	public void removeUser() {
		SQLiteDatabase database = this.getWritableDatabase();
		String deleteQuery = "DELETE FROM  user";
		database.execSQL(deleteQuery);
	}

	public void insertUser(String loginType,String name,String email,Bitmap bitmap,String imageUrl){
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("loginType",loginType);
		values.put("name",name);
		values.put("email",email);
		values.put("bitmap",getBytes(bitmap));
		values.put("imageUrl",imageUrl);
		database.insert("user", null, values);
		database.close();
	}

	public User getUser(){
		try {
			String selectQuery = "SELECT * FROM user";
			SQLiteDatabase database = this.getWritableDatabase();
			Cursor cursor = database.rawQuery(selectQuery, null);
			User user = new User();
			cursor.moveToNext();
			user.loginType = cursor.getString(0);
			user.name = cursor.getString(1);
			user.email = cursor.getString(2);
			byte[] image = cursor.getBlob(3);
			user.bitmap = getImage(image);
			user.imageUrl = cursor.getString(4);
			return user;
		}catch (Exception ex){
			return new User();
		}
	}

	// convert from bitmap to byte array
	public static byte[] getBytes(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
		return stream.toByteArray();
	}

	// convert from byte array to bitmap
	public static Bitmap getImage(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}

	public void DropTable(String table_Name)
	{
		String query = "DROP TABLE IF EXISTS Bookmarked";
		SQLiteDatabase database = this.getWritableDatabase();

		database.execSQL(query);
	}

}

