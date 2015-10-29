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
    /*
    public String uid = null;
    public String loginMethod = null;
    public String firstName = null;
    public String lastName = null;
    public String gender =null;
    public String interestedIn = null;
    public String birthday = null;
    public String dpUrl = null;
    public String originalDpUrl = null;
    public String profileUrl = null;
    public String email = null;
    public String phone = null;
    public Byte status = 0;
    public String token = null;
    public Byte age = 0;
    public String heightFeet =null;
    public String religion =null;
    public String relationshipStatus = null;
    public Short friends =0;
    public String hometown = null;
    public String companyName = null;
    public String position = null;
    public String salary = null;
    public String college = null;
    public String degree = null;
    public String socialLikes = null;
    public String interests = null;
    public String hobbies = null;
    public String currentCity = null;

    */

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
