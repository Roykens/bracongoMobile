package com.royken.bracongo.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.royken.bracongo.mobile.util.AndroidNetworkUtility;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
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
    @InjectView(R.id.link_url)
    TextView _urlLink;
    private String login;
    private String url;
    private String password;
    boolean isValide;
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean hasLoggedIn = settings.getBoolean("com.royken.hasLoggedIn", false);
        url = settings.getString("com.royken.url","");
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
                AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                String url = settings.getString("com.royken.url", "");
                if (!androidNetworkUtility.isConnected(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Aucune connexion au serveur. Veuillez reéssayer plus tard", Toast.LENGTH_LONG).show();
                } else {

                    login();
                }


            }
        });

        _urlLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
               // finish();
                showChangeLangDialog();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(true);

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
            _passwordText.setError("Mot de passe obligatoire");
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
        private boolean server = false;
        @Override
        protected void onPreExecute() {
            // showDialog(AUTHORIZING_DIALOG);
            Dialog.setMessage("Authenfification ...");
            Dialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Dialog.dismiss();
            if(!server){
                Toast.makeText(getApplicationContext(),"Aucune connexion au serveur. Veuillez reéssayer plus tard",Toast.LENGTH_LONG).show();
            }
            else{
                if(isValide){

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("com.royken.login", login);
                    editor.putString("com.royken.password",password);
                    //editor.putBoolean("com.royken.hasLoggedIn",true);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this,
                            SplashScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
                else {
                    Log.i("Connection...","Failllllllll");
                    Toast.makeText(getApplicationContext(),"Credential Error", Toast.LENGTH_LONG).show();
                }
            }

    }

        @Override
        protected Boolean doInBackground(String... params) {
            //Do all your slow tasks here but dont set anything on UI
            //ALL ui activities on the main thread
            AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
            url = settings.getString("com.royken.url","");
            if (!androidNetworkUtility.isConnected(getApplicationContext())) {
                server = false;
                isValide = false;
                return false;
            }

            else{
                server = true;
                try {
                    HttpGet httpGet = new HttpGet(url+"/bracongo/api/authenticate/"+login.trim()+"/"+password.trim());

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

                }catch(Exception hce){
                    server = false;
                    hce.printStackTrace();
                }

            }

            return true;
        }

    }

    private void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.url_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.input_url);
        if(url.length() > 0){
            edt.setText(url);
        }
        editor = settings.edit();
        dialogBuilder.setTitle("Modification de l'URL");
        dialogBuilder.setMessage("Entrer l'URL du serveur");
        dialogBuilder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String txt = edt.getText().toString().trim();
                editor.putString("com.royken.url",txt);
                editor.commit();
                Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_LONG).show();
            }
        });
        dialogBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                dialog.cancel();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
