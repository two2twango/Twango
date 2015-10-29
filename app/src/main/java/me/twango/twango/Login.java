package me.twango.twango;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.service.carrier.CarrierMessagingService;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.twango.twango.R;
import me.twango.twango.entity.NameConstant;
import me.twango.twango.entity.User;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.ImageDownloader;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import java.net.URI;
import java.util.Arrays;

/**
 * Created by AVIATER on 15-Oct-15.
 */
public class Login extends AppCompatActivity {
    CallbackManager callbackManager;
    Context context;
    /*
    User user = new User;

     */
    String name;
    String email;
    String uid;
    Bitmap bitmap;

    private static final String TAG = "ExampleActivity";
    private static final int REQUEST_CODE_RESOLVE_ERR = 9000;
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;
    private boolean mIntentInProgress;
    private ProgressDialog mConnectionProgressDialog;
    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;
    // private ConnectionResult mConnectionResult;

    SignInButton signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().hide();
        context = this;
        if(User.getInstance(this).loginType.equalsIgnoreCase("")){
            Intent getInfoActivity = new Intent(context,GetInfoActivity.class);
            startActivity(getInfoActivity);
        }
        callbackManager = CallbackManager.Factory.create();
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token != null) {
            Toast.makeText(this, token.getToken(), Toast.LENGTH_LONG).show();
        }
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton.setReadPermissions('email','user_friends','public_profile','user_relationships','user_birthday','user_relationship_details','user_hometown','user_likes','user_work_history','user_location','user_education_history');
        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            final JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {
                            //name = object.getString("name");
                            //email = object.getString("email");
                            //uid = object.getString("id");
                            /*user.uid = object.getString("uid");
                            user.loginMethod = 'Facebook';
                            user.token = object.getString("token");
                            user.firstName = object.contains('first_name') ? object.getString("first_name") : null;
                            user.lastName = object.contains('last_name') ? object.getString("last_name") : null;
                            user.email = object.contains('email') ? object.getString("email") : null;
                            user.gender = object.contains('gender') ? object.getString("gender") : null;
                            user.interestedIn = user.gender=='male'||user.gender==null ? 'female' : 'male';
                            user.profileUrl = object.contains('link') ? object.getString("link") : null;
                            user.birthday = object.contains('birthday') ? object.getString("birthday") : null;
                            user.age =
                            user.profileUrl = object.contains('link') ? object.getString("link") : null;
                            user.relationshipStatus = object.contains('relationship_status') ? object.getString("relationship_status") : null;
                            user.dpUrl = 'http://graph.facebook.com/v2.5/' + user.uid + '/picture?width=180&height=180';
                            user.originalDpUrl = 'http://graph.facebook.com/v2.5/' + user.uid + '/picture?width=1920';
                            user.friends =                                  // obj->friends->summary->total_count
                            user.college =                                 //obj->education->[education.length-1]->school->name
                            user.degree =                                  //obj->education->[education.length-1]->degree->name
                            user.companyName =                             //obj->work->[0]->employer->name
                            user.position =                             //obj->work->[0]->position->name
                            user.hometown =                             //obj->hometown->name
                            user.currentCity =                          //obj->location->name*/

                            ImageRequest.Builder requestBuilder = new ImageRequest.Builder(context, ImageRequest.getProfilePictureUri(object.getString("id"), 100, 100));
                            ImageRequest request = requestBuilder.setAllowCachedRedirects(true).setCallerTag(this).setCallback(new ImageRequest.Callback() {
                                public void onCompleted(ImageResponse response) {
                                    bitmap = response.getBitmap();
                                    Toast.makeText(context, "Signed In", Toast.LENGTH_LONG).show();
                                    try {
                                        //setResult(RESULT_OK);
                                        //finish();
                                        User.setUser(context,object,NameConstant.LOGIN_TYPE_FACEBOOK,bitmap,ImageRequest.getProfilePictureUri(object.getString("id"),100,100).toString());
                                        Intent getInfoActivity = new Intent(context, GetInfoActivity.class);
                                        startActivity(getInfoActivity);

                                    } catch (Exception ex) {

                                    }
                                }
                            }).build();
                            ImageDownloader.downloadAsync(request);
                        } catch (Exception ex) {

                        }
                        Log.v("LoginActivity", response.toString());
                    }
                });
                Bundle parameters = new Bundle();
                //  parameters.putString("fields", "id,name,first_name,last_name,age_range,link,verified,gender,email,locale,friends,friendlists,likes,work,relationship_status,hometown,location,birthday,education");
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Log.v("LoginActivity", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });

        /*signIn = (SignInButton)findViewById(R.id.sign_in_button);
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
                            @Override
                            public void onResult(People.LoadPeopleResult loadPeopleResult) {
                                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                                if (currentPerson != null) {
                                    name = currentPerson.getDisplayName();
                                    final String imageUrl = currentPerson.getImage().getUrl();
                                    Uri uri = Uri.parse(imageUrl);
                                    uid = currentPerson.getId();
                                    email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                                    ImageRequest.Builder requestBuilder = new ImageRequest.Builder(context, uri);
                                    ImageRequest request = requestBuilder.setAllowCachedRedirects(true).setCallerTag(this).setCallback(new ImageRequest.Callback() {
                                        public void onCompleted(ImageResponse response) {
                                            bitmap = response.getBitmap();
                                            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
                                            //User.setUser(context,uid, email, name, NameConstant.LOGIN_TYPE_GMAIL, bitmap, imageUrl);
                                            //setResult(RESULT_OK);
                                            //finish();
                                            Intent getInfoActivity = new Intent(context,GetInfoActivity.class);
                                            startActivity(getInfoActivity);
                                        }
                                    }).build();
                                    ImageDownloader.downloadAsync(request);
                                }else{
                                    Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        if (result.hasResolution()) {
                            try {
                                result.startResolutionForResult((Login)context, // your activity
                                        RC_SIGN_IN);
                                //startIntentSenderForResult(result.getResolution().getIntentSender(),
                                //      RC_SIGN_IN, null, 0, 0, 0);
                            } catch (IntentSender.SendIntentException e) {
                                // The intent was canceled before it was sent.  Return to the default
                                // state and attempt to connect to get an updated ConnectionResult.
                                mGoogleApiClient.connect();
                            }
                        }
                    }
                })
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.EMAIL))
                .addScope(new Scope(Scopes.PLUS_LOGIN))
                .addScope(new Scope(Scopes.PLUS_ME))
                .build();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleApiClient.connect();
            }
        });
*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

}
