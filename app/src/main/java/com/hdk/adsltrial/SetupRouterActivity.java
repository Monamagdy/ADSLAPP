package com.hdk.adsltrial;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hdk.adsltrial.router.RouterCommands;

/**
 * A login screen that offers login via email/password.
 */
public class SetupRouterActivity extends AppCompatActivity {

    // UI references.
    private EditText mWifiNameView;
    private EditText mWifiPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_router);
        // Set up the login form.
        mWifiNameView = findViewById(R.id.wifi_name);
       // populateAutoComplete();

        mWifiPasswordView = findViewById(R.id.wifi_password);

        ImageView mEmailSignInButton = (ImageView) findViewById(R.id.nextBtn);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                attemptLogin();
            }
        });

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

                } catch(Throwable dexp) {

                }
                printMessage(msg);
                return null;
            }
        }.execute();



    }
    public void printMessage (final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
       try{
            Thread.sleep(3000);}
            catch (Throwable s){

        }
                Intent intent = new Intent(SetupRouterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

