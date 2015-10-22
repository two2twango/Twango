package me.twango.twango;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;

/**
 * Created by AVIATER on 13-Oct-15.
 */
public class GetStarted extends Activity{
    ViewPager viewPager;
    Button button;
    GetStartedSwipeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.getstarted);
        viewPager =(ViewPager)findViewById(R.id.view_pager);
        adapter = new GetStartedSwipeAdapter(this);
        viewPager.setAdapter(adapter);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;

        button = (Button) findViewById(R.id.getStart);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Login.class);
                startActivity(intent);

            }

        });

    }
}
