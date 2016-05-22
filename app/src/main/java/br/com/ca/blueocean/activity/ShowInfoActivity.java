package br.com.ca.blueocean.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.com.ca.blueocean.preferences.PreferencesHelper;
import br.com.ca.blueocean.users.SignManager;
import br.com.ca.blueocean.vo.UserVo;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    /**
     * onOptionsItemSelected
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * callSynchronize
     *
     * @param v
     *
    public void callSynchronize(View v) {

        Intent intent;

        //Intent for SignIn activity
        intent = new Intent(ShowInfoActivity.this, SynchronizeInitiativesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //clear activity stack to return do signin activity
        //Start Intent
        startActivity(intent);

    }
    */

    /**
     * doSignOut
     *
     */
    public void doSignOut(View v){

        Intent intent;

        SignManager signManager = new SignManager(getApplicationContext());
        signManager.signOut();

        //Intent for SignIn activity
        intent = new Intent(ShowInfoActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //clear activity stack to return do signin activity
        //Start Intent
        startActivity(intent);

    }

}
