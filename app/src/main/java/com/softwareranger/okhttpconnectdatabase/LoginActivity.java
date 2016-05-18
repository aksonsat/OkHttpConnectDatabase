package com.softwareranger.okhttpconnectdatabase;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softwareranger.okhttpconnectdatabase.parser.JSONParserParams;
import com.softwareranger.okhttpconnectdatabase.utils.InternetConnection;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private TextView textViewLoginStatus;
    private Button btnLogin;

    // Progress Dialog
    private ProgressDialog pDialog;

    private JSONParserParams jsonParserParams = new JSONParserParams();

    // url to create new product
    private static String url_login = "http://192.168.43.128/okhttp/get_login.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    /**
     * Key to Send
     */
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWidget();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Checking Internet Connection
                 */
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    new GetLogin().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Internet Connection Not Available", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initWidget(){
        etUsername = (EditText) findViewById(R.id.editTextLoginUser);
        etPassword = (EditText) findViewById(R.id.editTextLoginPassword);
        textViewLoginStatus = (TextView) findViewById(R.id.textViewLoginStatus);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
    }

    /**
     * Background Async Task to Login
     * */
    class GetLogin extends AsyncTask<String, String, String> {

        String strUsername = etUsername.getText().toString();
        String strPassword = etPassword.getText().toString();

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("กำลังเชื่อมต่อกับฐานข้อมูล..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            Log.i("Check EditText", "U and P : " + strUsername + strPassword);

            // Building Parameters
            RequestBody formBody = new FormEncodingBuilder()
                    .add(KEY_USERNAME, strUsername)
                    .add(KEY_PASSWORD, strPassword)
                    .build();

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParserParams.makeHttpRequest(url_login,
                    "POST", formBody);

            try {
                success = json.getInt(TAG_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();

            //Log.i("Respone Success", " " + success);
            if (success == 1) {
                textViewLoginStatus.setText("เข้าสู่ระบบสำเร็จ");
            } else {
                textViewLoginStatus.setText("เข้าสู่ระบบไม่สำเร็จ");
            }
        }
    }
}
