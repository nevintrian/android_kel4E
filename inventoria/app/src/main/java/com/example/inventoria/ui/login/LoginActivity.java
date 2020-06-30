package com.example.inventoria.ui.login;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.inventoria.MainActivity;
import com.example.inventoria.MainActivity1;
import com.example.inventoria.R;
import com.example.inventoria.tools.SessionManager;
import com.example.inventoria.tools.Url;
import com.example.inventoria.ui.daftar.DaftarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    SessionManager sessionManager;
    TextView daftar;
    // Creating EditText.
    EditText Username, Password;

    // Creating button;
    Button LoginButton;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    String UsernameHolder, PasswordHolder;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
    String HttpUrl = Url.URL + "api/login/";

    Boolean CheckEditText;

    //String ServerResponse = null ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         daftar = (TextView) findViewById(R.id.daftar1);
         daftar.setOnClickListener(view -> {
              Intent intent = new Intent(LoginActivity.this, DaftarActivity.class);
                startActivity(intent);
                 });



        // Assigning ID's to EditText.
        Username = (EditText) findViewById(R.id.l_username);

        Password = (EditText) findViewById(R.id.l_password);

        // Assigning ID's to Button.
        LoginButton = (Button) findViewById(R.id.login);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(LoginActivity.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(LoginActivity.this);

        // Adding click listener to button.
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    UserLogin();

                } else {

                    Toast.makeText(LoginActivity.this, "Data belum diisi", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    // Creating user login function.
    public void UserLogin() {

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        try {
                        // Matching server responce message to our text.
                        JSONObject obj = new JSONObject(ServerResponse);
                        if(obj.optString("status").equals("true")) {
                            Toast.makeText(LoginActivity.this, obj.optString("message") + "", Toast.LENGTH_SHORT).show();
                            finish();
                            if (obj.optString("level").equals("admin") || obj.optString("level").equals("admin")) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                // Sending User Username to another activity using intent.

                                startActivity(intent);


                            } else if (obj.optString("level").equals("customer") || obj.optString("level").equals("sales")) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity1.class);

                                // Sending User Username to another activity using intent.

                                startActivity(intent);
                            }
                        }
                            sessionManager = new SessionManager(getApplicationContext());
                            sessionManager.createLoginSession(
                                    obj.getString("id_user"),
                                    obj.getString("email"),
                                    obj.getString("username"),
                                    obj.getString("password"),
                                    obj.getString("level"),
                                    obj.getString("nama"),
                                    obj.getString("tgl_lahir"),
                                    obj.getString("jenis_kelamin"),
                                    obj.getString("alamat"),
                                    obj.getString("no_telp"),
                                    obj.getString("foto")


                            );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(getApplicationContext(),"Login gagal, username dan password salah",Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("username", UsernameHolder);
                params.put("password", PasswordHolder);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    public void CheckEditTextIsEmptyOrNot() {

        // Getting values from EditText.
        UsernameHolder = Username.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(UsernameHolder) || TextUtils.isEmpty(PasswordHolder)) {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        } else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }
}