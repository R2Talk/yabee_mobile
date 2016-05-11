package br.com.ca.asap.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import br.com.ca.asap.users.SignManager;
import br.com.ca.shareview.R;

/**
 * Welcome full screen activity
 *
 */
public class WelcomeScreenActivity extends AppCompatActivity {

    private View mContentView;
    private TextView mUserNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome_screen);

        mContentView = findViewById(R.id.fullscreen_content);
        mUserNameView  = (TextView) findViewById(R.id.userNameTextView);

        // Hide UI and controls
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        SignManager signManager = new SignManager(getApplicationContext());
        String userName = signManager.getCurrentUser().getName();
        mUserNameView.setText(userName);

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeScreenActivity.this, SynchronizeInitiativesActivity.class);
                startActivity(intent);
            }
        });

    }
}
