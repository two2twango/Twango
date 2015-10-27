package me.twango.twango.entity;

import android.content.Context;
import android.graphics.Bitmap;
import me.twango.twango.helper.DBHelper;

/**
 * Created by Rathore on 9/26/2015.
 */
public  class User {
    public String name = "";
    public String email = "";
    public String imageUrl;
    public Bitmap bitmap;
    public String loginType = "";
    public String uid = "";
    static User me;

    public User(){
    }

    public static User getInstance(Context context){
        DBHelper dbHelper = new DBHelper(context);
        me = dbHelper.getUser();
        return me;
    }

    public static void setUser(Context context,String uid,String email,String name,String loginType,Bitmap bitmap,String imageUrl){
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.insertUser(loginType,uid,name,email,bitmap,imageUrl);
    }
}
