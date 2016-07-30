package jp.csrf.seccamp2016.securebookmark;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class ProvisionActivity extends AppCompatActivity {

    private final String INITIAL_URL = "https://seccamp2016.csrf.jp/bookmark/provision";
    public final static String CREDENTIALS = "Credentials";

    private Button createButton;
    private TextView usernameTextView;
    private TextView adminPasswordTextView;
    private TextView statusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provision);

        createButton = (Button) findViewById(R.id.createButton);
        usernameTextView = (TextView) findViewById(R.id.username);
        adminPasswordTextView = (TextView) findViewById(R.id.password);
        statusTextView = (TextView) findViewById(R.id.status);
        statusTextView.setText("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void onClickCreateButton(View v) {

        final String username = usernameTextView.getText().toString();
        final String adminPassword = adminPasswordTextView.getText().toString();

        Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
        if (p.matcher(username).find() && p.matcher(adminPassword).find()) {
            statusTextView.setText("Creating user...");
            createButton.setEnabled(false);
            AsyncTask<String, Void, String> task = new networkingTask();
            task.execute("username=" + username + "&" + "admin_password=" + adminPassword);
        } else {
            statusTextView.setText("Username and password must be alphanumeric.");
        }
    }

    private class networkingTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                HttpsURLConnection conn;
                URL url = new URL(INITIAL_URL);
                conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                OutputStream os = conn.getOutputStream();
                PrintStream ps = new PrintStream(os);
                ps.print(params[0]);
                ps.close();
                if (conn.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    result = reader.readLine();
                }
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                SharedPreferences prefs = getSharedPreferences(CREDENTIALS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", json.getString("username"));
                editor.putString("password1", json.getString("password1"));
                editor.putString("password2", json.getString("password2"));
                editor.putString("password3", json.getString("password3"));
                editor.putString("password4", json.getString("password4"));
                editor.putString("password5", json.getString("password5"));
                editor.apply();
                statusTextView.setText("Registration successful.");
            } catch (JSONException e) {
                statusTextView.setText("Registration failure.");
                e.printStackTrace();
            }
        }
    }
}


