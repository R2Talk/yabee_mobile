package br.com.ca.asap.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import br.com.ca.asap.preferences.PreferencesHelper;
import br.com.ca.asap.user.SignManager;
import br.com.ca.asap.vo.UserVo;
import br.com.ca.shareview.R;

/**
 * ShowInfoActivity
 *
 * Show information about logged user, last synchronization date and ya bee version.
 *
 * @author Rodrigo Carvalho
 */
public class ShowInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //action bar title
        setTitle(getString(R.string.info));

        //
        //info data
        //
        String currentUserName = null;
        String lastSyncDate = null;
        String currentYabeeVersion = "v1.0"; //TODO: get from compilation instance
        String developedBy = "";

        //
        //Get info values consulting preferences files and current user
        //

        //current user name
        SignManager signManager = new SignManager(getApplicationContext());
        UserVo userVo = signManager.getCurrentUser();
        currentUserName = userVo.getName();

        //last synch
        PreferencesHelper preferencesHelper = new PreferencesHelper(getApplicationContext(), PreferencesHelper.APP_PREFERENCES);
        lastSyncDate = preferencesHelper.getStringPrefrenceValue(PreferencesHelper.LAST_SYNC);

        //developed by
        developedBy = getString(R.string.developedBy);

        //
        //Set view values
        //
        TextView userNameTextView = (TextView) findViewById(R.id.userNameTextView);
        userNameTextView.setText(currentUserName);
        TextView lastSynchdateTextView = (TextView) findViewById(R.id.lastSynchDateTextView);
        lastSynchdateTextView.setText(lastSyncDate);
        TextView yaBeeVersionNumberTextView = (TextView) findViewById(R.id.yaBeeVersionNumberTextView);
        yaBeeVersionNumberTextView.setText(currentYabeeVersion);

    }

}
