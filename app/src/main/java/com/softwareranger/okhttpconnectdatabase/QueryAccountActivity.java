package com.softwareranger.okhttpconnectdatabase;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.softwareranger.okhttpconnectdatabase.adapter.Adapter;
import com.softwareranger.okhttpconnectdatabase.model.Model;
import com.softwareranger.okhttpconnectdatabase.parser.JSONParser;
import com.softwareranger.okhttpconnectdatabase.utils.InternetConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QueryAccountActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Model> list;
    private Adapter adapter;

    // url to create new product
    private static String url_list_account = "http://192.168.43.128/okhttp/get_list_account.php";

    private static String KEY_ACCOUNT = "account";
    private static String KEY_ID = "id";
    private static String KEY_USER = "user";
    private static String KEY_PASSWORD = "password";
    private static String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_name);

        /**
         * Array List for Binding Data from JSON to this List
         */
        list = new ArrayList<>();

        /**
         * Binding that List to Adapter
         */
        adapter = new Adapter(this, list);

        /**
         * Checking Internet Connection
         */
        if (InternetConnection.checkConnection(getApplicationContext())) {
            initWidget();
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "Internet Connection Not Available", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * InitViews
     */
    private void initWidget(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        new GetDataTask().execute();
    }

    /**
     * Creating Get Data Task for Getting Data From Web
     */
    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(QueryAccountActivity.this);
            dialog.setTitle("Please with...");
            dialog.setMessage("Downloading...");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser.makeHttp(url_list_account);

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {
                    /**
                     * Check Length...
                     */
                    if(jsonObject.length() > 0) {
                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        JSONArray array = jsonObject.getJSONArray(KEY_ACCOUNT);

                        /**
                         * Check Length of Array...
                         */
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for(int jIndex = 0; jIndex < lenArray; jIndex++) {

                                /**
                                 * Creating Every time New Object
                                 * and
                                 * Adding into List
                                 */
                                Model model = new Model();

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                int strId = innerObject.getInt(KEY_ID);
                                String strUser = innerObject.getString(KEY_USER);
                                String strPassword = innerObject.getString(KEY_PASSWORD);
                                String strName = innerObject.getString(KEY_NAME);

                                /**
                                 * Getting Object from Main Object
                                 */
                                /**
                                 * JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                 * String phone = phoneObject.getString(Keys.KEY_MOBILE);
                                 **/

                                model.setId(strId);
                                model.setUser(strUser);
                                model.setPassword(strPassword);
                                model.setName(strName);

                                /**
                                 * Adding name and phone concatenation in List...
                                 */
                                list.add(model);

                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            /**
             * Checking if List size if more than zero then
             * Update ListView
             */
            if(list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_LONG).show();
            }
        }
    }

}
