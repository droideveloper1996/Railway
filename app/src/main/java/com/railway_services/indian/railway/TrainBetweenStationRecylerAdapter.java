package com.railway_services.indian.railway;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhishek on 30-03-2018.
 */

public class TrainBetweenStationRecylerAdapter extends RecyclerView.Adapter<TrainBetweenStationRecylerAdapter.TrainBetweenStationViewHolder> {


    private Context mCtx;
    private ArrayList<TbSClass> getTrains;

    public TrainBetweenStationRecylerAdapter(Context mCtx, ArrayList<TbSClass> getTrains) {
        this.mCtx = mCtx;
        this.getTrains = getTrains;
    }

    @Override
    public TrainBetweenStationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrainBetweenStationViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.train_between_station, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(TrainBetweenStationViewHolder holder, int position) {

        holder.train_names.setText(getTrains.get(position).getName());
        holder.train_number.setText("(" + getTrains.get(position).getNumber() + ")");
        holder.source.setText(getTrains.get(position).getFrom_station_code());
        holder.destination.setText(getTrains.get(position).getTo_station_code());
        holder.arrival_time.setText(getTrains.get(position).getSrc_departure_time());
        holder.departureTime.setText(getTrains.get(position).getDest_arrival_time());

        Location source = new Location("");
        source.setLatitude(getTrains.get(position).getSource_lat());
        source.setLongitude(getTrains.get(position).getSource_long());

        Location destination = new Location("");
        destination.setLatitude(getTrains.get(position).getDestination_lat());
        destination.setLongitude(getTrains.get(position).getSource_long());

        int distanceInMeters = (int) source.distanceTo(destination) / 1000;
        // holder.speed.setText(distanceInMeters/getTrains.get(position).getTravel_time());
        holder.distance_time.setText(getTrains.get(position).getTravel_time() + "/" + distanceInMeters);

        Map<String, String> daysMap = new HashMap();
        Map<String, String> classesMap = new HashMap<>();
        daysMap = getTrains.get(position).getDaysMap();
        classesMap = getTrains.get(position).getClassesMap();

        Log.d("Days",daysMap.toString());
        if (daysMap.get("MON").equals("Y")) {
            holder.monday.setTextColor(mCtx.getResources().getColor(R.color.availableColor));
            holder.monday.setText("MON");
        }  if (daysMap.get("TUE").equals("Y")) {
            holder.tuesday.setTextColor(mCtx.getResources().getColor(R.color.availableColor));
            holder.tuesday.setText("TUE");

        }  if (daysMap.get("WED").equals("Y")) {
            holder.wednesday.setTextColor(mCtx.getResources().getColor(R.color.availableColor));
            holder.wednesday.setText("WED");


        } if (daysMap.get("THU").equals("Y")) {
            holder.thursday.setTextColor(mCtx.getResources().getColor(R.color.availableColor));
            holder.thursday.setText("THU");


        }  if (daysMap.get("FRI").equals("Y")) {
            holder.friday.setTextColor(mCtx.getResources().getColor(R.color.availableColor));
            holder.friday.setText("FRI");


        } if (daysMap.get("SAT").equals("Y")) {
            holder.saturday.setTextColor(mCtx.getResources().getColor(R.color.availableColor));
            holder.saturday.setText("SAT");


        }  if (daysMap.get("SUN").equals("Y")) {
            holder.sunday.setTextColor(mCtx.getResources().getColor(R.color.availableColor));
            holder.sunday.setText("SUN");


        }


    }

    @Override
    public int getItemCount() {
        return getTrains.size();
    }

    public class TrainBetweenStationViewHolder extends RecyclerView.ViewHolder {


        TextView train_names;
        TextView train_number;
        TextView departureTime;
        TextView arrival_time;
        TextView source;
        TextView destination;
        TextView speed;
        TextView distance_time;
        TextView sunday;
        TextView monday;
        TextView tuesday;
        TextView wednesday;
        TextView thursday;
        TextView friday;
        TextView saturday;


        public TrainBetweenStationViewHolder(View itemView) {
            super(itemView);

            train_names = itemView.findViewById(R.id.train_name);
            train_number = itemView.findViewById(R.id.train_number);
            departureTime = itemView.findViewById(R.id.departure);
            arrival_time = itemView.findViewById(R.id.arrival);
            source = itemView.findViewById(R.id.sstation);
            destination = itemView.findViewById(R.id.dstation);
            speed = itemView.findViewById(R.id.avg_speed);
            distance_time = itemView.findViewById(R.id.distance_time);
            sunday = itemView.findViewById(R.id.sunday);
            monday = itemView.findViewById(R.id.monday);
            tuesday = itemView.findViewById(R.id.tuesday);
            wednesday = itemView.findViewById(R.id.wednesday);
            thursday = itemView.findViewById(R.id.thursday);
            friday = itemView.findViewById(R.id.friday);
            saturday = itemView.findViewById(R.id.saturday);
        }
    }
}

