package com.railway_services.indian.railway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RailwayActivity extends AppCompatActivity {

    public static final String REQUEST_URL = "https://raw.githubusercontent.com/datameet/railways/master/stations.json";
    ArrayList<String> stationarraylist;
    ArrayList<TbSClass> listOfTrainBetweenStation;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NetworkUtils networkUtils = new NetworkUtils(this);
        getTrainBetweenStations("CNB", "NDLS", "14-05-2018");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8002141505808441/6284654946");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
            }
        });

        Button btn = findViewById(R.id.pnrBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RailwayActivity.this, PnrActivity.class));
            }
        });

    }

    void makeNetworkCall() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = REQUEST_URL;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String data = "";

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.optJSONArray("features");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject miniStation = jsonArray.getJSONObject(i);
                                JSONObject properties = miniStation.optJSONObject("properties");
                                data = "<item>" + properties.optString("code") + " (" + properties.optString("name") + ")" + "</item>";
                                Log.d("RailwayJson", data);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }

    void getTrainBetweenStations(String ss, String ds, String date) {
        listOfTrainBetweenStation = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = new ConstantUtils(this).trainsBetwenStaion(ss, ds, date);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            int responseCode = jsonObject.optInt(ConstantUtils.RESOPNSE_CODE);
                            int totalTrains = jsonObject.optInt(ConstantUtils.TOTAL);
                            Log.d("NETWOKUTILS", Integer.toString(totalTrains));
                            Log.d("NETWOKUTILS", Integer.toString(responseCode));

                            if (responseCode == 200) {

                                JSONArray trainsJsonArray = jsonObject.optJSONArray(ConstantUtils.TRAINS);
                                for (int i = 0; i < trainsJsonArray.length(); i++) {
                                    TbSClass tbSClass = new TbSClass();
                                    JSONObject trainsObject = trainsJsonArray.optJSONObject(i);
                                    tbSClass.setName(trainsObject.optString("name"));
                                    tbSClass.setNumber(trainsObject.optString("number"));
                                    tbSClass.setTravel_time(trainsObject.optString("travel_time"));
                                    tbSClass.setSrc_departure_time(trainsObject.optString("src_departure_time"));
                                    tbSClass.setDest_arrival_time(trainsObject.optString("dest_arrival_time"));

                                    JSONObject from_station = trainsObject.optJSONObject("from_station");

                                    tbSClass.setFrom_station_code(from_station.optString("code"));
                                    tbSClass.setFrom_station_name(from_station.optString("name"));
                                    tbSClass.setSource_lat(from_station.optDouble("lat"));
                                    tbSClass.setSource_long(from_station.optDouble("lng"));

                                    JSONObject to_station = trainsObject.optJSONObject("to_station");
                                    tbSClass.setTo_station_code(to_station.optString("code"));
                                    tbSClass.setTo_station_name(to_station.optString("name"));
                                    tbSClass.setDestination_lat(to_station.optDouble("lat"));
                                    tbSClass.setDestination_lng(to_station.optDouble("lng"));

                                    Log.d("NETWOKUTILS", trainsObject.optString("name"));
                                    Log.d("NETWOKUTILS", trainsObject.optString("number"));
                                    Log.d("NETWOKUTILS", trainsObject.optString("travel_time"));
                                    Log.d("NETWOKUTILS", trainsObject.optString("src_departure_time"));
                                    Log.d("NETWOKUTILS", trainsObject.optString("dest_arrival_time"));
                                    Log.d("NETWOKUTILS", from_station.optString("code"));
                                    Log.d("NETWOKUTILS", from_station.optString("name"));
                                    Map<String, String> daysMap = new HashMap<>();
                                    JSONArray daysJsonArray = trainsObject.optJSONArray(ConstantUtils.DAYS);
                                    for (int j = 0; j < daysJsonArray.length(); j++) {
                                        JSONObject daysJsonObject = daysJsonArray.optJSONObject(j);
                                        String code = daysJsonObject.optString("code");
                                        String runs = daysJsonObject.optString("runs");
                                        Log.d("NETWOKUTILS", daysJsonObject.optString("code"));
                                        Log.d("NETWOKUTILS", daysJsonObject.optString("runs"));

                                        daysMap.put(code, runs);
                                    }
                                    tbSClass.setDaysMap(daysMap);
                                    Map<String, String> classsesMap = new HashMap<>();
                                    JSONArray classesJsonArray = trainsObject.optJSONArray(ConstantUtils.CLASSES);
                                    for (int k = 0; k < classesJsonArray.length(); k++) {
                                        JSONObject classesJsonObject = classesJsonArray.optJSONObject(k);
                                        String code = classesJsonObject.optString("code");
                                        String availabe = classesJsonObject.optString("available");
                                        Log.d("NETWOKUTILS", classesJsonObject.optString("code"));
                                        Log.d("NETWOKUTILS", classesJsonObject.optString("available"));
                                        classsesMap.put(code, availabe);
                                    }
                                    tbSClass.setClassesMap(classsesMap);

                                    listOfTrainBetweenStation.add(tbSClass);
                                }

                                TrainBetweenStationRecylerAdapter trainBetweenStationRecylerAdapter = new TrainBetweenStationRecylerAdapter(RailwayActivity.this, listOfTrainBetweenStation);
                                recyclerView.setAdapter(trainBetweenStationRecylerAdapter);
                                if (trainBetweenStationRecylerAdapter.getItemCount() != 0) {
                                    progressBar.setVisibility(View.GONE);
                                }
                                Toast.makeText(RailwayActivity.this, listOfTrainBetweenStation.get(0).getName(), Toast.LENGTH_LONG).show();

                                //   Toast.makeText(RailwayActivity.this,"Total trains found "+(Integer.toString(listOfTrainBetweenStation.size())),Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RailwayActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slie_in_left, R.anim.slide_out_right);

    }




    void getLiveTrainStatus() {

    }
}
