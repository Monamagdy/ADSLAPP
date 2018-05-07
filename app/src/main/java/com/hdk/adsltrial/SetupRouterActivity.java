package com.hdk.adsltrial;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hdk.adsltrial.router.DisconnectedException;
import com.hdk.adsltrial.router.RouterCommands;

/**
 * A login screen that offers login via email/password.
 */
public class SetupRouterActivity extends AppCompatActivity {

    // UI references.
    private EditText mWifiNameView;
    private EditText mWifiPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mWifiNameView = findViewById(R.id.wifi_name);
       // populateAutoComplete();

        mWifiPasswordView = findViewById(R.id.wifi_password);
        mWifiPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
    public void attemptLogin(){
        final String wifi_name =  mWifiNameView.getText().toString();
        final String wifi_password = mWifiPasswordView.getText().toString();

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                RouterCommands router = RouterFactory.getInstance();

                String msg = "Updated successfully.";

                try {
                    boolean result = router.set("1", wifi_name, "0", "wpa2", "psk", wifi_password, true);

                   if(!result)
                        msg = "Failed to update.";

                    Toast.makeText(getApplicationContext(),
                            msg, Toast.LENGTH_LONG).show();

                } catch(DisconnectedException dexp) {

                    Toast.makeText(getBaseContext(),
                            msg, Toast.LENGTH_LONG).show();
                }

                return null;
            }
        }.execute();
    }
}

