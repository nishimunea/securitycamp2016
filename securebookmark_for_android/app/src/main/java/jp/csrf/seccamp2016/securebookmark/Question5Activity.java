package jp.csrf.seccamp2016.securebookmark;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class Question5Activity extends QuestionBaseActivity {

    private final String INITIAL_URL = "https://seccamp2016.csrf.jp/bookmark/ver5/login";
    private final String SECRET_COMMAND = "*#*#72779673#*#*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Question 5");

        webview.setWebChromeClient(new WebChromeClient() {
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                SharedPreferences prefs = getSharedPreferences(ProvisionActivity.CREDENTIALS, Context.MODE_PRIVATE);
                String username = prefs.getString("username", "");
                String password = prefs.getString("password5", "");

                try {
                    URL origin = new URL(view.getUrl());
                    String scheme = origin.getProtocol();
                    String host = origin.getHost();
                    if (!scheme.equals("https") || !(host.equals("seccamp2016.csrf.jp") || host.endsWith(".google.com"))) {
                        return false;
                    }
                } catch (MalformedURLException e) {
                    return false;
                }

                if (message.equals(SECRET_COMMAND)) {
                    try {
                        JSONObject creds = new JSONObject();
                        creds.put("username", username);
                        creds.put("password", password);
                        result.confirm(creds.toString());
                        result.cancel();
                        return true;
                    } catch (JSONException e) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        });

        webview.loadUrl(INITIAL_URL);
    }

    @Override
    protected void setPasswordManager(WebView view, String url) {
    }

}
