package com.hdk.adsltrial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.hdk.adsltrial.server.ElasticSender;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Button feedback = (Button) findViewById(R.id.feedback_button);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runFeedback();
            }
        });
    }

    private void runFeedback() {

        final RatingDialog ratingDialog = new RatingDialog.Builder(this)
                //.icon(drawable)
                //.session(7)
                //.threshold(3)
                .title("How was your experience with us?")
                .titleTextColor(R.color.black)
                .positiveButtonText("Not Now")
                .negativeButtonText("Never")
                .positiveButtonTextColor(R.color.white)
                .negativeButtonTextColor(R.color.grey_500)
                .formTitle("Submit Feedback")
                .formHint("Tell us where we can improve")
                .formSubmitText("Submit")
                .formCancelText("Cancel")
                .ratingBarColor(R.color.yellow)
                .playstoreUrl("YOUR_URL")
                .onThresholdCleared(new RatingDialog.Builder.RatingThresholdClearedListener() {
                    @Override
                    public void onThresholdCleared(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                        //do something

                        ElasticSender sender = new ElasticSender();
                        sender.sendFeedback("0232423423", rating);

                        ratingDialog.dismiss();
                    }
                })
                .onThresholdFailed(new RatingDialog.Builder.RatingThresholdFailedListener() {
                    @Override
                    public void onThresholdFailed(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                        //do something
                        //ratingDialog.dismiss();
                    }
                })
                .onRatingChanged(new RatingDialog.Builder.RatingDialogListener() {
                    @Override
                    public void onRatingSelected(float rating, boolean thresholdCleared) {

                    }
                })
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {

                    }
                }).build();

        ratingDialog.show();
    }
}
