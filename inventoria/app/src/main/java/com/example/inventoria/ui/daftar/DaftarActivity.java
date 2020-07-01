package com.example.inventoria.ui.daftar;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.inventoria.R;
import com.example.inventoria.tools.Url;
import com.example.inventoria.ui.login.LoginActivity;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DaftarActivity extends AppCompatActivity {

    TextView login;
    // Creating EditText.
    EditText Email;
    EditText Username;
    EditText Password;
    EditText Nama;
    Spinner Level;
    EditText Tgl_lahir;
    Spinner Jenis_kelamin ;

    // Creating button;
    Button Daftar;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    String EmailHolder, UsernameHolder, PasswordHolder, NamaHolder, LevelHolder, Tgl_lahirHolder, Jenis_kelaminHolder;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.


    String HttpUrl = Url.URL + "api/daftar/";

    Boolean CheckEditText ;

    public DaftarActivity() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        login = (TextView) findViewById(R.id.login1);


        login.setOnClickListener(view -> {
            Intent intent = new Intent(DaftarActivity.this, LoginActivity.class);

            // Sending User Email to another activity using intent.


            startActivity(intent);

        });


        final Calendar myCalendar = Calendar.getInstance();

        EditText edittext= (EditText) findViewById(R.id.d_tgl_lahir);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-mm-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                edittext.setText(sdf.format(myCalendar.getTime()));
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DaftarActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });





        Email = (EditText) findViewById(R.id.d_email);

        Username = (EditText) findViewById(R.id.d_username);

        Password = (EditText) findViewById(R.id.d_password);

        Nama = (EditText) findViewById(R.id.d_nama);

        Level = (Spinner) findViewById(R.id.d_level);



        Jenis_kelamin = (Spinner) findViewById(R.id.d_jenis_kelamin);

        // Assigning ID's to Button.
        Daftar = (Button) findViewById(R.id.daftar);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(DaftarActivity.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(DaftarActivity.this);

        // Adding click listener to button.
        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    UserRegistration();

                        Intent intent = new Intent(DaftarActivity.this, LoginActivity.class);

                        // Sending User Email to another activity using intent.

                        startActivity(intent);


                }
                else {

                    Toast.makeText(DaftarActivity.this, "data belum diisi", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    public void UserRegistration(){

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

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(getApplicationContext(),"Pendaftaran berhasil, silahkan login!",Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(getApplicationContext(),"Pendaftaran gagal",Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("email", EmailHolder);
                params.put("username", UsernameHolder);
                params.put("password", PasswordHolder);
                params.put("nama", NamaHolder);
                params.put("level", LevelHolder);
                params.put("tgl_lahir", Tgl_lahirHolder);
                params.put("jenis_kelamin", Jenis_kelaminHolder);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(DaftarActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    public void CheckEditTextIsEmptyOrNot(){

        // Getting values from EditText.
        EmailHolder = Email.getText().toString().trim();
        UsernameHolder = Username.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();
        NamaHolder = Nama.getText().toString().trim();
        LevelHolder = Level.getSelectedItem().toString().trim();
        Tgl_lahirHolder = Tgl_lahir.getText().toString().trim();
        Jenis_kelaminHolder = Jenis_kelamin.getSelectedItem().toString().trim();

        // Checking whether EditText value is empty or not.
        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(UsernameHolder) || TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(NamaHolder) || TextUtils.isEmpty(LevelHolder) || TextUtils.isEmpty(Tgl_lahirHolder) || TextUtils.isEmpty(Jenis_kelaminHolder))
        {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        }
        else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true ;
        }
    }





}

