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
    String name;
    String email;
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
        callbackManager = CallbackManager.Factory.create();
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token != null) {
            Toast.makeText(this, token.getToken(), Toast.LENGTH_LONG).show();
        }
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton.setReadPermissions(Arrays.asList("email, public_profile"));
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
                            name = object.getString("name");
                            email = object.getString("email");
                            ImageRequest.Builder requestBuilder = new ImageRequest.Builder(context, ImageRequest.getProfilePictureUri(object.getString("id"),100,100));
                            ImageRequest request = requestBuilder.setAllowCachedRedirects(true).setCallerTag(this).setCallback(new ImageRequest.Callback() {
                                public void onCompleted(ImageResponse response) {
                                    bitmap = response.getBitmap();
                                    Toast.makeText(context,"Signed In",Toast.LENGTH_LONG).show();
                                    try{
                                        User.setUser(context, email, name, NameConstant.LOGIN_TYPE_FACEBOOK, bitmap, ImageRequest.getProfilePictureUri(object.getString("id"), 100, 100).toString());
                                        //setResult(RESULT_OK);
                                        //finish();
                                        Intent getInfoActivity = new Intent(context,GetInfoActivity.class);
                                        startActivity(getInfoActivity);

                                    }catch (Exception ex){

                                    }
                                }
                            }).build();
                            ImageDownloader.downloadAsync(request);
                        }catch (Exception ex){

                        }
                        Log.v("LoginActivity", response.toString());
                    }
                });
                Bundle parameters = new Bundle();
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

        signIn = (SignInButton)findViewById(R.id.sign_in_button);
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
                                    email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                                    ImageRequest.Builder requestBuilder = new ImageRequest.Builder(context, uri);
                                    ImageRequest request = requestBuilder.setAllowCachedRedirects(true).setCallerTag(this).setCallback(new ImageRequest.Callback() {
                                        public void onCompleted(ImageResponse response) {
                                            bitmap = response.getBitmap();
                                            Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
                                            User.setUser(context, email, name, NameConstant.LOGIN_TYPE_GMAIL, bitmap, imageUrl);
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
