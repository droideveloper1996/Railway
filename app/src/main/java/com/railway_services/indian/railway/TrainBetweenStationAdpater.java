package com.railway_services.indian.railway;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Abhishek on 04-03-2018.
 */

public class TrainBetweenStationAdpater extends RecyclerView.Adapter<TrainBetweenStationAdpater.TrainBetweenStationViewHolder> {

    private Context mCtx;

    public TrainBetweenStationAdpater(Context context) {
        this.mCtx = context;
    }

    @Override
    public TrainBetweenStationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrainBetweenStationViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.ticket_list_view,parent,false));
    }

    @Override
    public void onBindViewHolder(TrainBetweenStationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TrainBetweenStationViewHolder extends RecyclerView.ViewHolder {
        public TrainBetweenStationViewHolder(View itemView) {
            super(itemView);
        }
    }
}
