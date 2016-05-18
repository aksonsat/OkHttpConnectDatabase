package com.softwareranger.okhttpconnectdatabase;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softwareranger.okhttpconnectdatabase.parser.JSONParserParams;
import com.softwareranger.okhttpconnectdatabase.utils.InternetConnection;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUser, etPassword, etName;
    Button btnSubmit;

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParserParams jsonParserParams = new JSONParserParams();

    // url to create new product
    private static String url_sign_up = "http://192.168.43.128/okhttp/create_account.php";

    // JSON Node names
    private static final String KEY_SUCCESS = "success";

    /**
     * Key to Send
     */
    private static final String KEY_USER = "user";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initWidget();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * Checking Internet Connection
                 */
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    new SignUpNewAccount().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Internet Connection Not Available", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initWidget(){
        etUser = (EditText) findViewById(R.id.editTextUser);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        etName = (EditText) findViewById(R.id.editName);
        btnSubmit = (Button) findViewById(R.id.buttonSubmit);
    }

    /**
     * Background Async Task to Create new product
     * */
    class SignUpNewAccount extends AsyncTask<String, String, String> {

        String user = etUser.getText().toString();
        String password = etPassword.getText().toString();
        String name = etName.getText().toString();

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            RequestBody formBody = new FormEncodingBuilder()
                    .add(KEY_USER, user)
                    .add(KEY_PASSWORD, password)
                    .add(KEY_NAME, name)
                    .build();

            Log.i("Check Params", "Params = " + formBody);
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParserParams.makeHttpRequest(url_sign_up,
                    "POST", formBody);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(KEY_SUCCESS);

                if (success == 1) {
                    Log.i("Check Success","Success = " + success);
                } else {
                    Log.i("Check Success","Success = " + success);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }
}
