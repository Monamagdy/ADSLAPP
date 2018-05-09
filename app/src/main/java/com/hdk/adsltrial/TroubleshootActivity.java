package com.hdk.adsltrial;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.hdk.adsltrial.router.DSLinfo;
import com.hdk.adsltrial.router.DisconnectedException;
import com.hdk.adsltrial.router.RouterCommands;
import com.hdk.adsltrial.server.TimeLineAdapter;
import com.hdk.adsltrial.server.TimeLineModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monamagdy on 5/7/18.
 */

public class TroubleshootActivity extends AppCompatActivity {

    ImageView statusImage;
    TextView tv;
    TextView qty;

    TableRow row;
    TableRow.LayoutParams lp;
    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troubleshoot);
      //  ll = findViewById(R.id.table_layout);

        mDataList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        mDataList.add(new TimeLineModel("Getting ADSL info.."));
//
//        mDataList.add(new TimeLineModel("Testing Connection"));
//
//        mDataList.add(new TimeLineModel("Restarting Router..."));
//
//        mDataList.add(new TimeLineModel("Reconnecting to router.."));
//
//        mDataList.add(new TimeLineModel("Testing Connection"));
//
//        mDataList.add(new TimeLineModel("Let's check the router"));

        mTimeLineAdapter = new TimeLineAdapter(mDataList);
        //mTimeLineAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mTimeLineAdapter);


        // initView();
        getRouterStatus();

    }

    public void getRouterStatus() {

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                RouterCommands router = RouterFactory.getInstance();
                String msg = "Updated successfully.";

                try {
                   pushMessages("Getting ADSL info..");
                    // adsl info
                    // displayMessage("Getting ADSL info..", "");
                    DSLinfo result = router.displayDSL();
                    String status = result != null ? result.getStatus() : null;
                    // setProgress(true);

                    if (status != null && status.equals("DSL down!")) {   //getting adsl info
                        // ping the server
                        pushMessages("Testing Connection");
                        // displayMessage("Testing Connection", "No connection");
                        Boolean pingStatus = router.ping("8.8.8.8");
                        //   setProgress(true);

                        if (!pingStatus) {    //testing connection - no connection
                            pushMessages("Restarting Router...");
                            //    displayMessage("Restarting Router...", "");
                            Boolean restartRouter = router.reboot();  //restarting router
                            //     setProgress(true);
                            pushMessages("Reconnecting to router..");
                            //  displayMessage("Reconnecting to router..", "");
                            router.reconnect(20);
                            //     setProgress(true);

                            // ping again
                            pushMessages("Testing Connection");
                            //  displayMessage("Testing Connection", "No connection");
                            Boolean pingStatus2 = router.ping("8.8.8.8");
                            //   setProgress(true);
                            if (!pingStatus2) {
                              //  pushMessages("Let's check the router");
                                // displayMessage("Let's check the router", "");
                            }
                        }
                    }

                } catch (DisconnectedException dexp) {

                    Toast.makeText(getBaseContext(),
                            msg, Toast.LENGTH_LONG).show();
                }

                return null;
            }


        }.execute();
    }

    public void pushMessages(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(mDataList.size() > 0) {
                    mDataList.get(mDataList.size() - 1).setStatus(TimeLineModel.STATUS.INACTIVE);
                }

                TimeLineModel timeLineModel = new TimeLineModel(msg, TimeLineModel.STATUS.ACTIVE);
                mDataList.add(timeLineModel);
                mTimeLineAdapter = new TimeLineAdapter(mDataList);
                //mTimeLineAdapter = new TimeLineAdapter(mDataList);
                mRecyclerView.setAdapter(mTimeLineAdapter);
                mTimeLineAdapter.notifyDataSetChanged();

                try {
                    Thread.sleep(2000);
                } catch(InterruptedException ex) {

                }
                //mTimeLineAdapter.notifyDataSetChanged();

            }
        });
    }

    public void displayMessage(final String message1, final String message2) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

//                row = new TableRow(TroubleshootActivity.this);
//                lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//                //    row.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.benefits_cards_background));
//                row.setLayoutParams(lp);
//                row.setPadding(20, 20, 5, 20);
//                statusImage = new ImageView(TroubleshootActivity.this);
//
//                //progressBar = new ProgressBar(TroubleshootActivity.this);
//                //progressBar.setIndeterminate(true);
//
//                tv = new TextView(TroubleshootActivity.this);
//                tv.setText(message1);
//                tv.setTextSize(22);
//                tv.setTextColor(Color.WHITE);
//                qty = new TextView(TroubleshootActivity.this);
//                qty.setText(message2);
//
//                row.addView(statusImage);
//                row.addView(tv);
//                //row.addView(qty);
//                //row.addView(addBtn);
//                ll.addView(row);
            }
        });
    }

    public void setProgress(final boolean done) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException iexp) {
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
