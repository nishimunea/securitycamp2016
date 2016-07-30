package jp.csrf.seccamp2016.securebookmark;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;

public class Question1Activity extends QuestionBaseActivity {

    private final String INITIAL_URL = "https://seccamp2016.csrf.jp/bookmark/ver1/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Question 1");
        webview.loadUrl(INITIAL_URL);
    }

    @Override
    protected void setPasswordManager(WebView view, String url) {
        SharedPreferences prefs = getSharedPreferences(ProvisionActivity.CREDENTIALS, Context.MODE_PRIVATE);
        String username = prefs.getString("username", "");
        String password = prefs.getString("password1", "");

        // Fill out login form automatically
        String source = "";
        source += "document.getElementById('bookmark_username').value='" + username + "';";
        source += "document.getElementById('bookmark_password').value='" + password + "';";
        webview.evaluateJavascript(source, null);
    }

}
