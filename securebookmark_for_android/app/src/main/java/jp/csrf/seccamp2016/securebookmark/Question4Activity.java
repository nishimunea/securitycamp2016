package jp.csrf.seccamp2016.securebookmark;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;
import java.net.MalformedURLException;
import java.net.URL;

public class Question4Activity extends QuestionBaseActivity {

    private final String INITIAL_URL = "https://seccamp2016.csrf.jp/bookmark/ver4/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Question 4");
        webview.loadUrl(INITIAL_URL);
    }

    @Override
    protected void setPasswordManager(WebView view, String url) {
        SharedPreferences prefs = getSharedPreferences(ProvisionActivity.CREDENTIALS, Context.MODE_PRIVATE);
        String username = prefs.getString("username", "");
        String password = prefs.getString("password4", "");

        try {
            URL origin = new URL(view.getUrl());
            String scheme = origin.getProtocol();
            String host = origin.getHost();
            String func = origin.getRef();
            if (scheme.equals("https") && host.equals("seccamp2016.csrf.jp")) {
                // Fill out login form automatically
                String source = func + "('" + username + "','" + password + "')";
                webview.evaluateJavascript(source, null);
            }
        } catch (MalformedURLException e) {
            // Do nothing
        }
    }
}
