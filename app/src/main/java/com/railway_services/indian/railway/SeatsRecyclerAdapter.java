package com.railway_services.indian.railway;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SeatsRecyclerAdapter extends RecyclerView.Adapter<SeatsRecyclerAdapter.SeatsViewHolder> {

    private Context mctx;
    public ArrayList<SeatsAvailInfo> seatsAvailInfoArrayList;

    public SeatsRecyclerAdapter(Context context, ArrayList<SeatsAvailInfo> seatsAvailInfos) {
        this.mctx = context;
        this.seatsAvailInfoArrayList = seatsAvailInfos;

    }

    @Override
    public SeatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SeatsViewHolder(LayoutInflater.from(mctx).inflate(R.layout.seats_availability, parent, false));
    }

    @Override
    public void onBindViewHolder(SeatsViewHolder holder, int position) {
        holder.dateOfSearch.setText(seatsAvailInfoArrayList.get(position).getmDate());
        holder.SeatsAvailable.setText(seatsAvailInfoArrayList.get(position).getmAvailablility());
    }

    @Override
    public int getItemCount() {
        return seatsAvailInfoArrayList.size();
    }

   public class SeatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView SeatsAvailable;
        TextView dateOfSearch;
        public SeatsViewHolder(View itemView) {
            super(itemView);
            SeatsAvailable=itemView.findViewById(R.id.available_seats);
            dateOfSearch=itemView.findViewById(R.id.date);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
