package com.example.inventoria.tools;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.inventoria.ui.login.LoginActivity;

import org.json.JSONArray;


public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidRetrofitLogin";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_TGL_LAHIR = "tgl_lahir";
    public static final String KEY_JENIS_KELAMIN = "jenis_kelamin";
    public static final String KEY_ALAMAT = "alamat";
    public static final String KEY_NO_TELP = "no_telp";
    public static final String KEY_FOTO = "foto";
    // Id(make variable public to access from outside)
    public static final String KEY_ID = "id_user";




    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * @param id_user
     */
    public void createLoginSession(String id_user, String email, String username, String password, String level, String nama, String tgl_lahir, String jenis_kelamin, String alamat, String no_telp, String foto) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_ID, id_user);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_LEVEL, level);
        editor.putString(KEY_NAMA, nama);
        editor.putString(KEY_TGL_LAHIR, tgl_lahir);
        editor.putString(KEY_JENIS_KELAMIN, jenis_kelamin);
        editor.putString(KEY_ALAMAT, alamat);
        editor.putString(KEY_NO_TELP, no_telp);
        editor.putString(KEY_FOTO, foto);


        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }


    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
    public String getKeyId_user() {
        return pref.getString(KEY_ID, null);
    }
    public String getKeyEmail() { return pref.getString(KEY_EMAIL, null); }
    public String getKeyUsername() {
        return pref.getString(KEY_USERNAME, null);
    }
    public String getKeyPassword() {
        return pref.getString(KEY_PASSWORD, null);
    }
    public String getKeyLevel() { return pref.getString(KEY_LEVEL, null); }
    public String getKeyNama() {
        return pref.getString(KEY_NAMA, null);
    }
    public String getKeyTgl_lahir() {
        return pref.getString(KEY_TGL_LAHIR, null);
    }
    public String getKeyJenis_kelamin() {
        return pref.getString(KEY_JENIS_KELAMIN, null);
    }
    public String getKeyAlamat() {
        return pref.getString(KEY_ALAMAT, null);
    }
    public String getKeyNo_telp() { return pref.getString(KEY_NO_TELP, null); }
    public String getKeyFoto() { return pref.getString(KEY_FOTO, null); }



}
