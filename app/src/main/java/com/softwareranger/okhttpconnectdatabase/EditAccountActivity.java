package com.softwareranger.okhttpconnectdatabase;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.softwareranger.okhttpconnectdatabase.parser.JSONParser;
import com.softwareranger.okhttpconnectdatabase.parser.JSONParserParams;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditAccountActivity extends AppCompatActivity {

    private int accountId;
    private String strAccountId;

    private TextView textViewEditId, textViewEditUser;
    private EditText editTextEditPassword, editTextEditName;

    // Progress Dialog
    private ProgressDialog pDialog;

    private int strId;
    private String strUser, strPassword, strName;

    // single product url
    private static final String url_account_detials = "http://192.168.43.128/okhttp/get_account_details.php";

    private static final String KEY_ID = "id";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_ACCOUNT = "account";
    private static final String KEY_USER = "user";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";

    JSONParserParams jsonParserParams = new JSONParserParams();
    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Bundle bundle = getIntent().getExtras();
        accountId = bundle.getInt("AC_ID");
        strAccountId = String.valueOf(accountId);

        /**
         * Query Detail Account
         */
        new GetAccountDetails().execute();
    }

    /**
     * Background Async Task to Get complete product details
     * */
    class GetAccountDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditAccountActivity.this);
            pDialog.setMessage("Loading account details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         * */
        protected String doInBackground(final String... params) {

                    // Check for success tag
                    int success;
                    try {

                        String url_get = url_account_detials + "?" + KEY_ID + "=" + strAccountId;
                        // getting product details by making HTTP request
                        // Note that product details url will use GET request
                        /**
                        JSONObject json = jsonParserParams.makeHttpRequest(
                                url_account_detials, "GET", formBody);
                         **/

                        JSONObject json = jsonParser.makeHttp(
                                url_get);
                        // check your log for json response
                        // Log.d("Single Product Details", json.toString());

                        // json success tag
                        success = json.getInt(KEY_SUCCESS);
                        Log.i("Check success", "Success = " + success);

                        if (success == 1) {
                            // successfully received product details
                            JSONArray accountObj = json
                                    .getJSONArray(KEY_ACCOUNT); // JSON Array

                            // get first product object from JSON Array
                            JSONObject account = accountObj.getJSONObject(0);

                            // product with this pid found
                            // Edit Text And TextView
                            textViewEditId = (TextView) findViewById(R.id.textViewEditId);
                            textViewEditUser = (TextView) findViewById(R.id.textViewEditUser);
                            editTextEditPassword = (EditText) findViewById(R.id.editTextEditPassword);
                            editTextEditName = (EditText) findViewById(R.id.editTextEditName);

                            strId = account.getInt(KEY_ID);
                            strUser = account.getString(KEY_USER);
                            strPassword = account.getString(KEY_PASSWORD);
                            strName = account.getString(KEY_NAME);

                        }else{
                            // product with pid not found
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
            // dismiss the dialog once got all details
            pDialog.dismiss();

            Log.i("Check Str", "Str = " + strId + strUser + strPassword + strName);

            // display product data in EditText

            textViewEditId.setText(String.valueOf(strId));
            textViewEditUser.setText(strUser);
            editTextEditPassword.setText(strPassword);
            editTextEditName.setText(strName);

        }
    }
}
