package com.hdk.adsltrial.server;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.hdk.adsltrial.R;
import com.hdk.adsltrial.Utils;

import java.util.List;

/**
 * Created by monamagdy on 5/9/18.
 */

public class TimeLineAdapter extends  RecyclerView.Adapter<TimeLineAdapter.MyViewHolder>  {
    private List<TimeLineModel> moviesList;

    private Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title, year, genre;
    TimelineView mTimelineView;

    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.textKO);
        mTimelineView = view.findViewById(R.id.time_marker);
//        genre = (TextView) view.findViewById(R.id.genre);
//        year = (TextView) view.findViewById(R.id.year);
    }
}



    public TimeLineAdapter(List<TimeLineModel> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.troubleshoot_message_row, parent, false);

        mContext = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TimeLineModel movie = moviesList.get(position);
           holder.title.setText(movie.getMessage());
//        holder.genre.setText(movie.getGenre());
//        holder.year.setText(movie.getYear());

        if(movie.getStatus() == TimeLineModel.STATUS.INACTIVE) {
            holder.mTimelineView.setMarker(Utils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
        } else if(movie.getStatus() == TimeLineModel.STATUS.ACTIVE) {
            holder.mTimelineView.setMarker(Utils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorPrimary));
        } else {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}