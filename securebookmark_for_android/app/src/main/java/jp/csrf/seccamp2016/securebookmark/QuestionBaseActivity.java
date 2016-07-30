package jp.csrf.seccamp2016.securebookmark;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

abstract public class QuestionBaseActivity extends AppCompatActivity {

    private Button historyBackButton;
    private Button historyForwardButton;

    WebView webview;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        historyBackButton = (Button) findViewById(R.id.historyBackButton);
        historyForwardButton = (Button) findViewById(R.id.historyForwardButton);
        webview = (WebView) findViewById(R.id.webView);

        webview.clearCache(true);
        webview.clearFormData();
        webview.clearHistory();

        WebSettings webSettings = webview.getSettings();
        webSettings.setUserAgentString("Mozilla/5.0 SecureBookmark");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSaveFormData(false);
        webSettings.setAllowFileAccess(false);
        webSettings.setAllowContentAccess(false);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                historyBackButton.setEnabled(view.canGoBack());
                historyForwardButton.setEnabled(view.canGoForward());
                setPasswordManager(view, url);
            }

        });

        webview.setWebChromeClient(new WebChromeClient());
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void onClickHistoryBackButton(View v) {
        webview.goBack();
    }

    public void onClickHistoryForwardButton(View v) {
        webview.goForward();
    }

    public void onClickRefreshButton(View v) {
        webview.reload();
    }

    abstract void setPasswordManager(WebView view, String url);

}
