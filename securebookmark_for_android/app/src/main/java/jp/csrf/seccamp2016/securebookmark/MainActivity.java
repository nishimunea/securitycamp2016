package jp.csrf.seccamp2016.securebookmark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickQuestion1Button(View v) {
        Intent i = new Intent(this, Question1Activity.class);
        startActivity(i);
    }

    public void onClickQuestion2Button(View v) {
        Intent i = new Intent(this, Question2Activity.class);
        startActivity(i);
    }

    public void onClickQuestion3Button(View v) {
        Intent i = new Intent(this, Question3Activity.class);
        startActivity(i);
    }

    public void onClickQuestion4Button(View v) {
        Intent i = new Intent(this, Question4Activity.class);
        startActivity(i);
    }

    public void onClickQuestion5Button(View v) {
        Intent i = new Intent(this, Question5Activity.class);
        startActivity(i);
    }

    public void onClickProvisionButton(View v) {
        Intent i = new Intent(this, ProvisionActivity.class);
        startActivity(i);
    }
}
