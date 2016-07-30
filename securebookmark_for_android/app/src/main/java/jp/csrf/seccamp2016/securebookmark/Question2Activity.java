package jp.csrf.seccamp2016.securebookmark;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;

public class Question2Activity extends QuestionBaseActivity {

    private final String INITIAL_URL = "https://seccamp2016.csrf.jp/bookmark/ver2/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Question 2");
        webview.loadUrl(INITIAL_URL);
    }

    @Override
    protected void setPasswordManager(WebView view, String url) {
        SharedPreferences prefs = getSharedPreferences(ProvisionActivity.CREDENTIALS, Context.MODE_PRIVATE);
        String username = prefs.getString("username", "");
        String password = prefs.getString("password2", "");

        if (url.startsWith("http") && url.indexOf("seccamp2016.csrf.jp") > 0 && url.endsWith("/bookmark/ver2/login")) {
            // Fill out login form automatically
            String source = "";
            source += "document.getElementById('username').value='" + username + "';";
            source += "document.getElementById('password').value='" + password + "';";
            webview.evaluateJavascript(source, null);
        }
    }
}
