package com.railway_services.indian.railway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PnrActivity extends AppCompatActivity {


    TextView trains_name;
    TextView trains_number;
    TextView pnr_numbers;
    TextView departureTime;
    TextView arrivalTime;
    TextView sourceStationCode;
    TextView destinationStationCode;
    TextView sourceStationName;
    TextView destinationStationName;
    TextView dateOFJourney;
    TextView boardingStation;
    TextView journeyClass;
    TextView passenger;
    TextView serialNo;
    TextView bookingStatus1;
    TextView currentStatus1;
    ProgressBar progressBar;
    TextView chartStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr);
        if (getIntent() != null) {
            String pnr = getIntent().getStringExtra("PNR_NUMBER");
            getPnrStatus(pnr);

        }
        trains_name = findViewById(R.id.train_name);
        trains_number = findViewById(R.id.train_number);
        pnr_numbers = findViewById(R.id.pnr_number);
        departureTime = findViewById(R.id.departure_time);
        arrivalTime = findViewById(R.id.arrival_time);
        sourceStationCode = findViewById(R.id.source_station_code);
        destinationStationCode = findViewById(R.id.destination_station_code);
        sourceStationName = findViewById(R.id.source_station_name);
        destinationStationName = findViewById(R.id.destination_station_name);
        dateOFJourney = findViewById(R.id.date_of_journey);
        boardingStation = findViewById(R.id.boarding_station);
        journeyClass = findViewById(R.id.class_category);
        serialNo = findViewById(R.id.sno);
        bookingStatus1 = findViewById(R.id.bookingStatus);
        currentStatus1 = findViewById(R.id.currentStatus);
        progressBar = findViewById(R.id.pnrProgress);
        chartStatus = findViewById(R.id.chartStatus);
    }

    void getPnrStatus(String pnr) {


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = new ConstantUtils(this).builPnrurl(PnrActivity.this, pnr);
        Log.d("Pnr Url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("PNR Response", response);
                        boolean chartPrepared;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int statusCode = jsonObject.optInt("response_code");
                            if (statusCode == 200) {
                                String pnr = jsonObject.optString("pnr");
                                String doj = jsonObject.optString("doj");
                                chartPrepared = jsonObject.optBoolean("chart_prepared");
                                String totalPassengers = jsonObject.optString("total_passengers");
                                JSONObject fromStation = jsonObject.optJSONObject("from_station");
                                String sCode = fromStation.optString("code");
                                String sName = fromStation.optString("name");
                                JSONObject toStation = jsonObject.optJSONObject("to_station");
                                String dSCode = toStation.optString("code");
                                String dSName = toStation.optString("name");
                                JSONObject boardingPoint = jsonObject.optJSONObject("boarding_point");
                                String bName = boardingPoint.optString("name");
                                String bCode = boardingPoint.optString("code");
                                JSONObject trainInfo = jsonObject.optJSONObject("train");
                                String trainName = trainInfo.optString("name");
                                String trainNUmber = trainInfo.optString("number");
                                JSONObject journey_class = jsonObject.optJSONObject("journey_class");
                                String Classname = journey_class.optString("name");
                                String Classcode = journey_class.optString("code");

                                pnr_numbers.setText(pnr);
                                dateOFJourney.setText(doj);
                                sourceStationCode.setText(sCode);
                                sourceStationName.setText(sName);
                                destinationStationCode.setText(dSCode);
                                destinationStationName.setText(dSName);
                                boardingStation.setText(bName);
                                trains_name.setText(trainName);
                                trains_number.setText(trainNUmber);
                                journeyClass.setText(Classcode);


                                JSONArray passengers = jsonObject.optJSONArray("passengers");
                                String passengerListSno = "";
                                String passengerBookingStatus = "";
                                String currentBooking = "";
                                for (int i = 0; i < passengers.length(); i++) {

                                    JSONObject passengerJsonObject = passengers.optJSONObject(i);
                                    String number = passengerJsonObject.optString("no");
                                    String currentStatus = passengerJsonObject.optString("current_status");
                                    String bookingStatus = passengerJsonObject.optString("booking_status");
                                    currentBooking += currentStatus + '\n';
                                    passengerBookingStatus += bookingStatus + '\n';
                                    passengerListSno += number + '\n';
                                    // passengerList = number + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + bookingStatus + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + '\t' + currentStatus + '\n';


                                }
                                // Log.d("PNR Response Passeng", passengerList);
                                currentStatus1.setText(currentBooking);
                                bookingStatus1.setText(passengerBookingStatus);
                                serialNo.setText(passengerListSno);

                                progressBar.setVisibility(View.GONE);

                                if (chartPrepared) {
                                    chartStatus.setText("C H A R T    P R E P A R E D");
                                    chartStatus.setTextColor(getResources().getColor(R.color.greenStatus));
                                } else {
                                    chartStatus.setText("C H A R T    NOT   P R E P A R E D");
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PnrActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);


    }
}
