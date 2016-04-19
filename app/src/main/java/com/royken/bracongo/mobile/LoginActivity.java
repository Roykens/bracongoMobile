package com.royken.bracongo.mobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.royken.bracongo.mobile.util.AndroidNetworkUtility;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by royken on 18/04/16.
 */
public class LoginActivity extends Activity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    public static final String PREFS_NAME = "com.royken.MyPrefsFile";
    public static final String PREFS_LOGIN_NAME = "login";
    public static final String PREFS_PASSWD_NAME = "password";

    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    //@InjectView(R.id.link_signup)
    //TextView _signupLink;
    private String login;
    private String password;
    boolean isValide;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean hasLoggedIn = settings.getBoolean("com.royken.hasLoggedIn", false);
        if(hasLoggedIn == true){
            Intent intent = new Intent(LoginActivity.this,
                    SplashScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            LoginActivity.this.finish();
        }

        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        // final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        // progressDialog.setIndeterminate(true);
        // progressDialog.setMessage("Authenticating...");
        // progressDialog.show();

        login = _emailText.getText().toString();
        password = _passwordText.getText().toString();
        new LoginABackgroundTask().execute();

        // TODO: Implement your own authentication logic here.

      /*  new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                        if(validate()){
                            Intent intent = new Intent(MainActivity.this,
                                    SplashScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }
                    }
                }, 3000);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() ) {
            _emailText.setError("Login obligatoire");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    class LoginABackgroundTask extends AsyncTask<String, Integer, Boolean> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(LoginActivity.this);
        String data ="";
        @Override
        protected void onPreExecute() {
            // showDialog(AUTHORIZING_DIALOG);
            Dialog.setMessage("Authenfification ...");
            Dialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Dialog.dismiss();
            if(isValide){

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("com.royken.login", login);
                editor.putString("com.royken.password",password);
                editor.putBoolean("com.royken.hasLoggedIn",true);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this,
                        SplashScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                LoginActivity.this.finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Credential Error", Toast.LENGTH_LONG);
            }


        }

        @Override
        protected Boolean doInBackground(String... params) {
            //Do all your slow tasks here but dont set anything on UI
            //ALL ui activities on the main thread

            HttpGet httpGet = new HttpGet("http://10.0.2.2:8080/bracongo/api/authenticate/"+login.trim()+"/"+password.trim());

            //setting header to request for a JSON response
            httpGet.setHeader("Accept", "application/json");
            AndroidNetworkUtility httpUtil = new AndroidNetworkUtility();
            String productJSONStr = httpUtil.getHttpResponse(httpGet);
            Log.d("", "Response: " + productJSONStr);
            try {
                JSONObject obj = new JSONObject(productJSONStr);
                isValide = obj.getBoolean("isvalide");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;

        }

    }
}
