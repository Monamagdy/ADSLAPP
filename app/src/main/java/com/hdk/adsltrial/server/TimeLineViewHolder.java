package com.hdk.adsltrial.server;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.vipulasri.timelineview.TimelineView;
import com.hdk.adsltrial.R;

/**
 * Created by monamagdy on 5/9/18.
 */
public class TimeLineViewHolder extends RecyclerView.ViewHolder {
    public TimelineView mTimelineView;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);
        mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
        mTimelineView.initLine(viewType);
    }

}