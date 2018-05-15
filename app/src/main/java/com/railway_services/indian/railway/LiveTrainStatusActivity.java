package com.railway_services.indian.railway;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LiveTrainStatusActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    ArrayList<String> stationArray;
    Spinner spinner;

    TextView trainName;
    TextView trainNumer;
    TextView scheduleArrival;
    TextView scheduleDeparture;
    TextView actualArrival;
    TextView actualDepartue;
    TextView lastLocation;
    TextView delay;
    TextView hasArrivedatStation;
    TextView hasDepartedfromStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_train_status);
        trainName=findViewById(R.id.trainName);
        trainNumer=findViewById(R.id.trainNo);
        scheduleArrival=findViewById(R.id.scheduleArrival);
        scheduleDeparture=findViewById(R.id.scheduleDeparture);
        actualArrival=findViewById(R.id.actualArrival);
        actualDepartue=findViewById(R.id.actualDeparture);
        lastLocation=findViewById(R.id.lastLocation);
        delay=findViewById(R.id.delay);
        hasArrivedatStation=findViewById(R.id.hasArrived);
        hasDepartedfromStation=findViewById(R.id.hasDeparted);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        stationArray = new ArrayList<>();
        //spinner = findViewById(R.id.stationSpinner);
       // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stationArray);
      //  spinner.setAdapter(arrayAdapter);
        getLiveTrainStatsu("12033", "25-04-2018");
    }


    void getLiveTrainStatsu(final String trainNumber, String date) {
        RequestQueue queue = Volley.newRequestQueue(LiveTrainStatusActivity.this);

        String url = ConstantUtils.buildLiveTrainUrl(trainNumber, date);
        Log.d("Live", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Live Train Status", response.toString());
                        Boolean hasArrived;
                        Boolean hasDeparted;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int response_code = jsonObject.optInt("response_code");
                            if (response_code == 200) {

                                String position = jsonObject.optString("position");
                                JSONObject trainInfo = jsonObject.optJSONObject("train");
                                String train_number = trainInfo.optString("number");
                                String train_name = trainInfo.optString("name");
                                JSONArray routeArray = jsonObject.optJSONArray("route");

                                for (int i = 0; i < routeArray.length(); i++) {

                                    JSONObject routeObject = routeArray.optJSONObject(i);

                                    JSONObject station = routeObject.optJSONObject("station");
                                    String stationNAme = station.optString("name");
                                    String stationCode = station.optString("code");
                                    hasArrived = routeObject.optBoolean("has_arrived");
                                    hasDeparted = routeObject.optBoolean("has_departed");
                                    String distance = routeObject.optString("distance");
                                    String scheduleArrivals = routeObject.optString("scharr");
                                    String scheduleDeparure = routeObject.optString("schdep");
                                    String actualArrivals = routeObject.optString("actarr");
                                    String actualDeparture = routeObject.optString("actdep");
                                    String scheduleArrialDate = routeObject.optString("scharr_date");
                                    String actualArrivalDate = routeObject.optString("actarr_date");
                                    String late = routeObject.optString("latemin");

                                    stationArray.add(stationNAme);
                                    /**
                                     *
                                     * lastlocation,
                                     * act arrival
                                     * schedule arrivl
                                     * actual departure
                                     * schedule depatte
                                     * doj
                                     * late by
                                     * current position
                                     * journey station
                                     * train name
                                     */
                                    lastLocation.setText(position);
                                    trainName.setText(train_name);
                                    trainNumer.setText(train_number);
                                    scheduleArrival.setText(scheduleArrivals);
                                    scheduleDeparture.setText(scheduleDeparure);
                                    actualArrival.setText(actualArrivals);
                                    actualDepartue.setText(actualDeparture);
                                    delay.setText(late);
                                    if(hasArrived)
                                    hasArrivedatStation.setText("Arrived");
                                    hasArrivedatStation.setText("Not Arrived");
                                    if(hasDeparted)
                                    hasDepartedfromStation.setText("Departed");
                                    hasDepartedfromStation.setText("Not Departed");
                                }
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(LiveTrainStatusActivity.this, android.R.layout.simple_list_item_1, stationArray);
                                spinner.setAdapter(arrayAdapter);


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
