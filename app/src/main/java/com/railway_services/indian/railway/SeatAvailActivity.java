package com.railway_services.indian.railway;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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

import java.util.ArrayList;

public class SeatAvailActivity extends AppCompatActivity {

    private static CoordinatorLayout coordinatorLayout;
    private static RecyclerView seatsRecyclerView;
    private static SeatsRecyclerAdapter seatsRecyclerAdapter;
    private static ArrayList<SeatsAvailInfo> seatsAvailInfoArrayList;
    private static TextView trainNumbers;
    private static TextView trainNam;
    private static TextView sourceStationcode;
    private static TextView destnationStationcode;
    private static TextView sourceStationName;
    private static TextView destinationStationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_avail);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        seatsRecyclerView = findViewById(R.id.seatsRecyclerView);
        seatsAvailInfoArrayList = new ArrayList<>();
        seatsRecyclerAdapter = new SeatsRecyclerAdapter(this, seatsAvailInfoArrayList);
        trainNumbers = findViewById(R.id.train_number);
        trainNam = findViewById(R.id.train_name);
        sourceStationcode = findViewById(R.id.source_station_code);
        sourceStationName = findViewById(R.id.source_station_name);
        destinationStationName = findViewById(R.id.destination_station_name);
        destnationStationcode = findViewById(R.id.destination_station_code);
        seatsRecyclerView.setHasFixedSize(true);
        seatsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSeatAvailibility(this, "12033", "CNB",
                "NDLS", "29-06-2018", "CC", "GN");

    }

    static void getSeatAvailibility(final Context context, String trainNumber, final String sourceStationCode,
                                    final String destinationStationCode, String date, String classCode, String quota) {


        String actionUrl = ConstantUtils.buildSeatAvailabilityUrl(trainNumber, sourceStationCode, destinationStationCode, date, classCode, quota);
        seatsAvailInfoArrayList.clear();
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d("Live", actionUrl);
        final SeatsAvailInfo seatsAvailInfo;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, actionUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Seat Available", response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int responseCode = jsonObject.optInt("response_code");
                            if (responseCode == 200) {
                                JSONObject trainDetail = jsonObject.optJSONObject("train");

                                String trainNumber1 = trainDetail.optString("number");
                                String trainName1 = trainDetail.optString("name");

                                JSONObject fromStation = jsonObject.optJSONObject("from_station");
                                String fromStationCode1 = fromStation.optString("code");
                                String fromStationName1 = fromStation.optString("name");
                                String fromStationLat1 = fromStation.optString("lat");
                                String fromStationLng1 = fromStation.optString("lng");

                                JSONObject toStation = jsonObject.optJSONObject("to_station");
                                String toStationCode1 = toStation.optString("code");
                                String toStationName1 = toStation.optString("name");
                                String toStationLat1 = toStation.optString("lat");
                                String toStationLng1 = toStation.optString("lng");

                                JSONObject journeyClass = jsonObject.optJSONObject("journey_class");
                                String journeyClassName1 = journeyClass.optString("name");
                                String journeyClassCode1 = journeyClass.optString("code");

                                JSONObject quota = jsonObject.optJSONObject("quota");
                                String journeyQuotaName1 = quota.optString("name");
                                String journeyQuotaCode1 = quota.optString("code");

                                JSONArray jsonArray = jsonObject.optJSONArray("availability");

                                trainNumbers.setText(trainNumber1);
                                trainNam.setText(trainName1);

                                sourceStationcode.setText(fromStationCode1);
                                sourceStationName.setText(fromStationName1);

                                destnationStationcode.setText(toStationCode1);
                                destinationStationName.setText(toStationName1);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);

                                    String date = jsonObject1.optString("date");
                                    String status = jsonObject1.optString("status");
                                    SeatsAvailInfo seatsAvailInfo = new SeatsAvailInfo(date, status);
                                    seatsAvailInfoArrayList.add(seatsAvailInfo);
                                }

                                seatsRecyclerAdapter = new SeatsRecyclerAdapter(context, seatsAvailInfoArrayList);
                                seatsRecyclerView.setAdapter(seatsRecyclerAdapter);


                            } else {
                                Toast.makeText(context, "Error Fetching Railway Data..Please try again later", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(LiveTrainStatusActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Servers seems to be busy, try again later", Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        });

        queue.add(stringRequest);


    }
}
