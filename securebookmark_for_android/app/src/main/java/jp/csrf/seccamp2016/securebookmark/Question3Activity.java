package jp.csrf.seccamp2016.securebookmark;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

public class Question3Activity extends QuestionBaseActivity {

    private final String INITIAL_URL = "https://seccamp2016.csrf.jp/bookmark/ver3/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Question 3");
        webview.addJavascriptInterface(new PasswordManager(), "passwordmanager");
        webview.loadUrl(INITIAL_URL);
    }

    @Override
    protected void setPasswordManager(WebView view, String url) {
    }

    class PasswordManager {
        @JavascriptInterface
        public String fill() {
            SharedPreferences prefs = getSharedPreferences(ProvisionActivity.CREDENTIALS, Context.MODE_PRIVATE);
            String username = prefs.getString("username", "");
            String password = prefs.getString("password3", "");
            try {
                JSONObject creds = new JSONObject();
                creds.put("username", username);
                creds.put("password", password);
                return creds.toString();
            } catch (JSONException e) {
                return "";
            }
        }
    }
}
