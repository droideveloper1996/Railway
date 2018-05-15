package com.railway_services.indian.railway;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhishek on 04-03-2018.
 */

public class NetworkUtils {

    ArrayList<TbSClass> listOfTrainBetweenStation = new ArrayList<>();
    private Context mCtx;
    String res = "";
    TbSClass tbSClass;
    public NetworkUtils() {

    }

    public NetworkUtils(Context mCtx) {
        this.mCtx = mCtx;
    }




    ArrayList<TbSClass> getTrainBetweenStations(String ss, String ds, String date) {

        RequestQueue queue = Volley.newRequestQueue(mCtx);
        String url = new ConstantUtils(mCtx).trainsBetwenStaion(ss, ds, date);

        tbSClass = new TbSClass();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int responseCode = jsonObject.optInt(ConstantUtils.RESOPNSE_CODE);
                            int totalTrains = jsonObject.optInt(ConstantUtils.TOTAL);
                            Log.d("NETWOKUTILS",Integer.toString(totalTrains));
                            Log.d("NETWOKUTILS",Integer.toString(responseCode));

                            if (responseCode == 200) {

                                JSONArray trainsJsonArray = jsonObject.optJSONArray(ConstantUtils.TRAINS);
                                for (int i = 0; i < trainsJsonArray.length(); i++) {
                                    JSONObject trainsObject = trainsJsonArray.optJSONObject(i);
                                    tbSClass.setName(trainsObject.optString("name"));
                                    tbSClass.setNumber(trainsObject.optString("number"));
                                    tbSClass.setTravel_time(trainsObject.optString("travel_time"));
                                    tbSClass.setSrc_departure_time(trainsObject.optString("src_departure_time"));
                                    tbSClass.setDest_arrival_time(trainsObject.optString("dest_arrival_time"));

                                    JSONObject from_station = trainsObject.optJSONObject("from_station");

                                    tbSClass.setFrom_station_code(from_station.optString("code"));
                                    tbSClass.setFrom_station_name(from_station.optString("name"));

                                    JSONObject to_station = trainsObject.optJSONObject("to_station");
                                    tbSClass.setTo_station_code(to_station.optString("code"));
                                    tbSClass.setTo_station_name(to_station.optString("name"));



                                    Log.d("NETWOKUTILS",trainsObject.optString("name"));
                                    Log.d("NETWOKUTILS",trainsObject.optString("number"));
                                    Log.d("NETWOKUTILS",trainsObject.optString("travel_time"));
                                    Log.d("NETWOKUTILS",trainsObject.optString("src_departure_time"));
                                    Log.d("NETWOKUTILS",trainsObject.optString("dest_arrival_time"));
                                    Log.d("NETWOKUTILS",from_station.optString("code"));
                                    Log.d("NETWOKUTILS",from_station.optString("name"));




                                    Map<String, String> daysMap = new HashMap<>();
                                    JSONArray daysJsonArray = trainsObject.optJSONArray(ConstantUtils.DAYS);
                                    for (int j = 0; j < daysJsonArray.length(); j++) {
                                        JSONObject daysJsonObject = daysJsonArray.optJSONObject(j);
                                        String code = daysJsonObject.optString("code");
                                        String runs = daysJsonObject.optString("runs");
                                        daysMap.put(code, runs);
                                    }
                                    tbSClass.setDaysMap(daysMap);
                                    Map<String, String> classsesMap = new HashMap<>();
                                    JSONArray classesJsonArray = trainsObject.optJSONArray(ConstantUtils.CLASSES);
                                    for (int k = 0; k < classesJsonArray.length(); k++) {
                                        JSONObject classesJsonObject = classesJsonArray.optJSONObject(k);
                                        String code = classesJsonObject.optString("code");
                                        String availabe = classesJsonObject.optString("available");
                                        classsesMap.put(code, availabe);
                                    }
                                    tbSClass.setClassesMap(classsesMap);


                                }

                                listOfTrainBetweenStation.add(tbSClass);
                                Toast.makeText(mCtx,"TOTal trains found "+(Integer.toString(listOfTrainBetweenStation.size())),Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mCtx, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

        return listOfTrainBetweenStation;
    }

}
