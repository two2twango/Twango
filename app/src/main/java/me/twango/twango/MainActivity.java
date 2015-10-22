package me.twango.twango;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        SharedPreferences prefs1 = getSharedPreferences("appOpenCount", Context.MODE_PRIVATE);
        int appOpenCount = prefs1.getInt("appOpenCount", 0);
        appOpenCount++;
        SharedPreferences.Editor editor1 = getSharedPreferences("appOpenCount", Context.MODE_PRIVATE).edit();
        editor1.putInt("appOpenCount", appOpenCount);
        editor1.commit();
        mHandler.postDelayed(new LoadActivitiesThread(), 2000);
    }

    public class LoadActivitiesThread implements Runnable {
        public void run() {
            loadActivities();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    private void loadActivities(){
        Intent home = new Intent(this, GetStarted.class);
        startActivity(home);
        finish();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
