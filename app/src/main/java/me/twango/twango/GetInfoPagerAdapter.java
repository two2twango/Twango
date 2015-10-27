package me.twango.twango;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import me.twango.twango.Network.MyRequestQueue;
import me.twango.twango.Network.VolleyData;
import me.twango.twango.entity.User;

/**
 * Created by AVIATER on 13-Oct-15.
 */
public class GetInfoPagerAdapter extends PagerAdapter{
    private Context context;
    private LayoutInflater layoutInflater;
    public GetInfoPagerAdapter(Context context)
    {
        this.context = context;
    }
    @Override
    public int getCount(){
        return 1;
    }
    @Override
    public boolean isViewFromObject(View view,Object o){
        return (view==(RelativeLayout)o);
    }
    @Override
    public Object instantiateItem(ViewGroup container,int position) {
        if (position==0){
            return loadBasicInfo(container);
        }else if (position==1){
            return loadWorkEducation(container);
        }else if (position==2){
            return loadInterests(container);
        }else if (position==3){
            return loadHobbies(container);
        }else if (position==4){
            return loadEmailMobile(container);
        }else if (position==5){
            return loadThankYou_Message(container);
        }else {
            return error(container);
        }

    }

    private View loadBasicInfo(ViewGroup container){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.basicinfo,container,false);
        User user = User.getInstance(context);
        String URL = "http://twango.me/users/api/basicinfo/"+user.uid+"/"+user.loginType;
        MyRequestQueue.Instance(context).cancelPendingRequests("Basicinfo");
        new VolleyData(context){

            @Override
            protected void VPreExecute() {

            }

            @Override
            protected void VResponse(JSONObject response, String tag) {
                Toast.makeText(context, "sucesss", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void VError(VolleyError error, String tag) {
                Toast.makeText(context, "error", Toast.LENGTH_LONG).show();
            }
        }.getJsonObject(URL, true, "Basicinfo");
        container.addView(item_view);
        return item_view;
    }

    private View loadWorkEducation(ViewGroup container){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.workeducation,container,false);
        User user = User.getInstance(context);
        String URL = "http://twango.me/users/api/basicinfo/"+user.uid+"/"+user.loginType;
        MyRequestQueue.Instance(context).cancelPendingRequests("workeducation");
        new VolleyData(context){

            @Override
            protected void VPreExecute() {

            }

            @Override
            protected void VResponse(JSONObject response, String tag) {


            }

            @Override
            protected void VError(VolleyError error, String tag) {

            }
        }.getJsonObject(URL, true, "workeducation");
        container.addView(item_view);
        return item_view;
    }
    private View loadInterests(ViewGroup container){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.interests,container,false);
        User user = User.getInstance(context);
        String URL = "http://twango.me/users/api/basicinfo/"+user.uid+"/"+user.loginType;
        MyRequestQueue.Instance(context).cancelPendingRequests("interests");
        new VolleyData(context){

            @Override
            protected void VPreExecute() {

            }

            @Override
            protected void VResponse(JSONObject response, String tag) {


            }

            @Override
            protected void VError(VolleyError error, String tag) {

            }
        }.getJsonObject(URL, true, "interests");

        container.addView(item_view);
        return item_view;
    }

    private View loadHobbies(ViewGroup container){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.hobbies,container,false);
        User user = User.getInstance(context);
        String URL = "http://twango.me/users/api/basicinfo/"+user.uid+"/"+user.loginType;
        MyRequestQueue.Instance(context).cancelPendingRequests("hobbies");
        new VolleyData(context){

            @Override
            protected void VPreExecute() {

            }

            @Override
            protected void VResponse(JSONObject response, String tag) {


            }

            @Override
            protected void VError(VolleyError error, String tag) {

            }
        }.getJsonObject(URL, true, "hobbies");
        container.addView(item_view);
        return item_view;
    }

    private View loadEmailMobile(ViewGroup container){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.emailmobile,container,false);
        User user = User.getInstance(context);
        String URL = "http://twango.me/users/api/basicinfo/"+user.uid+"/"+user.loginType;
        MyRequestQueue.Instance(context).cancelPendingRequests("emailmobile");
        new VolleyData(context){

            @Override
            protected void VPreExecute() {

            }

            @Override
            protected void VResponse(JSONObject response, String tag) {


            }

            @Override
            protected void VError(VolleyError error, String tag) {

            }
        }.getJsonObject(URL, true, "emailmobile");
        container.addView(item_view);
        return item_view;
    }

    private View loadThankYou_Message(ViewGroup container){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.thankyou,container,false);
        User user = User.getInstance(context);
        String URL = "http://twango.me/users/api/basicinfo/"+user.uid+"/"+user.loginType;
        MyRequestQueue.Instance(context).cancelPendingRequests("ThankYou");
        new VolleyData(context){

            @Override
            protected void VPreExecute() {

            }

            @Override
            protected void VResponse(JSONObject response, String tag) {


            }

            @Override
            protected void VError(VolleyError error, String tag) {

            }
        }.getJsonObject(URL, true, "thankyou");
        container.addView(item_view);
        return item_view;
    }

    private View error(ViewGroup container){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.error_404,container,false);
        container.addView(item_view);
        return item_view;
    }
    @Override
    public void destroyItem(ViewGroup container,int position,Object object){

        container.removeView((RelativeLayout)object);

    }

}
