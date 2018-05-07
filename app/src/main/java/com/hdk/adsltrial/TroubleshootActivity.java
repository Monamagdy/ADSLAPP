package com.hdk.adsltrial;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.hdk.adsltrial.router.DSLinfo;
import com.hdk.adsltrial.router.DisconnectedException;
import com.hdk.adsltrial.router.RouterCommands;

/**
 * Created by monamagdy on 5/7/18.
 */

public class TroubleshootActivity extends AppCompatActivity {

    ImageView statusImage;
    TextView tv;
    TextView qty;
    TableLayout ll;
    TableRow row;
    TableRow.LayoutParams lp;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troubleshoot);
         ll = findViewById(R.id.table_layout);

        addRowsDynamically();
    }

    public void addRowsDynamically(){
       getRouterStatus();
    }

    public void getRouterStatus(){

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

            RouterCommands router = RouterFactory.getInstance();
            String msg = "Updated successfully.";

            try {

                // adsl info
                displayMessage("Getting ADSL info..", "");
                DSLinfo result = router.displayDSL();
                String status = result != null? result.getStatus() : null;
                setProgress(true);

                if(status != null && status.equals("DSL down!")) {   //getting adsl info
                    // ping the server
                    displayMessage("Testing Connection", "No connection");
                    Boolean pingStatus =  router.ping("8.8.8.8");
                    setProgress(true);

                    if(!pingStatus) {    //testing connection - no connection

                        displayMessage("Restarting Router...", "");
                        Boolean restartRouter = router.reboot();  //restarting router
                        setProgress(true);

                        displayMessage("Reconnecting to router", "");
                        router.reconnect(20);
                        setProgress(true);

                        // ping again
                        displayMessage("Testing Connection", "No connection");
                        Boolean pingStatus2 = router.ping("8.8.8.8");
                        setProgress(true);
                        if (!pingStatus2) {
                            displayMessage("Let's check the router", "");
                        }
                    }
                }

            } catch(DisconnectedException dexp) {

                Toast.makeText(getBaseContext(),
                        msg, Toast.LENGTH_LONG).show();
            }

            return null;
            }
        }.execute();
    }

    public void displayMessage(final String message1, final String message2) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                row= new TableRow(TroubleshootActivity.this);
                lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);

                statusImage = new ImageView(TroubleshootActivity.this);

                //progressBar = new ProgressBar(TroubleshootActivity.this);
                //progressBar.setIndeterminate(true);

                tv = new TextView(TroubleshootActivity.this);
                tv.setText(message1);

                qty = new TextView(TroubleshootActivity.this);
                qty.setText(message2);

                row.addView(statusImage);
                row.addView(tv);
                //row.addView(qty);
                //row.addView(addBtn);
                ll.addView(row);
            }
        });
    }

    public void setProgress(final boolean done) {

        try {
            Thread.sleep(2000);
        } catch(InterruptedException iexp) {
            // ignore
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                progressBar.setVisibility(View.GONE);
                if (done)
                    statusImage.setImageResource(R.mipmap.ic_done);
                else
                    statusImage.setImageResource(R.mipmap.ic_error);
            }
        });
    }
}
