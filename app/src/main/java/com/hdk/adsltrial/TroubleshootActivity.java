package com.hdk.adsltrial;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
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

    CheckBox checkBox;
    TextView tv;
    TextView qty;
    TableLayout ll;
    TableRow row;
    TableRow.LayoutParams lp;
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
                    DSLinfo result = router.displayDSL();
                    String status = result != null? result.getStatus() : null;

                    if(status != null && status.equals("DSL down!")) {   //getting adsl info
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                displayMessage("Getting ADSL info..", "");
                            }
                        });

                        Boolean pingStatus =  router.ping("8.8.8.8");
                        if(!pingStatus){    //testing connection - no connection
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    displayMessage("Testing Connection", "No connection");
                                }
                            });

                      Boolean restartRouter = router.reboot();  //restarting router
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    displayMessage("Restarting Router...","");
                                }
                            });

                            router.reconnect(20);
                            Boolean pingStatus2 =  router.ping("8.8.8.8");

                        if(!pingStatus2){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    displayMessage("Testing Connection", "No connection");
                                    displayMessage("Let's check the router", "");
                                }
                            });
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
        public void displayMessage(String message1, String message2){
            row= new TableRow(this);
            lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            checkBox = new CheckBox(this);
            tv = new TextView(this);
            checkBox = new CheckBox(this);
            tv = new TextView(this);
            qty = new TextView(this);
            checkBox.setText(message1);
            qty.setText(message2);
            row.addView(checkBox);
            row.addView(qty);
            //row.addView(addBtn);
            ll.addView(row);
        }

}
