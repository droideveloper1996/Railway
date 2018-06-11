package com.railway_services.indian.railway;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import org.w3c.dom.Text;

import java.util.ArrayList;

public class CancelledTrainActivity extends AppCompatActivity {


    RecyclerView cancelledRecyclerView;
    ArrayList<CancelledTrains> cancelledTrainsArrayList;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_train);
        cancelledTrainsArrayList = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);

        cancelledRecyclerView = findViewById(R.id.cancelledRecyclerView);
        cancelledRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cancelledRecyclerView.setLayoutManager(linearLayoutManager);

        CancelledTrainRecyclerViewAdpater cancelledTrainAdapter = new CancelledTrainRecyclerViewAdpater(this, cancelledTrainsArrayList);
        cancelledRecyclerView.setAdapter(cancelledTrainAdapter);
        getCancelledTrains("10-06-2018");
    }

    void getCancelledTrains(String Date) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ConstantUtils.getCancelledTrain(Date);
        Log.d("Cancelled train", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.optInt("response_code");
                            if (status == 200) {
                                JSONArray jsonArray = jsonObject.optJSONArray("trains");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject cancelledObj = jsonArray.optJSONObject(i);

                                    String name = cancelledObj.optString("name");
                                    String number = cancelledObj.optString("number");
                                    String type = cancelledObj.optString("type");
                                    String time = cancelledObj.optString("start_time");

                                    JSONObject sourceObject = cancelledObj.optJSONObject("source");
                                    JSONObject destinationObject = cancelledObj.optJSONObject("dest");

                                    String dname = destinationObject.optString("name");
                                    String dcode = destinationObject.optString("code");

                                    String sname = sourceObject.optString("name");
                                    String scode = sourceObject.optString("code");


                                    CancelledTrains cancelledTrains = new CancelledTrains();
                                    cancelledTrains.setmDestinationCode(dcode);
                                    cancelledTrains.setmDestinationName(dname);
                                    cancelledTrains.setmSourceCode(scode);
                                    cancelledTrains.setmSourceName(sname);
                                    cancelledTrains.setmName(name);
                                    cancelledTrains.setmTime(time);
                                    cancelledTrains.setmType(type);
                                    cancelledTrains.setmNumber(number);

                                    cancelledTrainsArrayList.add(cancelledTrains);

                                }
                                CancelledTrainRecyclerViewAdpater cancelledTrainAdapter = new CancelledTrainRecyclerViewAdpater(CancelledTrainActivity.this, cancelledTrainsArrayList);
                                cancelledRecyclerView.setAdapter(cancelledTrainAdapter);

                                if (cancelledTrainAdapter.getItemCount() > 0) {
                                    progressBar.setVisibility(View.GONE);
                                }

                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CancelledTrainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);


    }

    class CancelledTrainRecyclerViewAdpater extends RecyclerView.Adapter<CancelledTrainRecyclerViewAdpater.CancelledTrainRecylerViewHolder> {

        private Context mCtx;
        private ArrayList<CancelledTrains> cancelledTrains;


        public CancelledTrainRecyclerViewAdpater(Context mCtx, ArrayList<CancelledTrains> cancelledTrains) {
            this.cancelledTrains = cancelledTrains;
            this.mCtx = mCtx;
        }

        @Override
        public CancelledTrainRecylerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CancelledTrainRecylerViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.cancelled_list, parent, false));
        }

        @Override
        public void onBindViewHolder(CancelledTrainRecylerViewHolder holder, int position) {
            holder.sourceStationName.setText(cancelledTrains.get(position).getmSourceName());
//            holder.sourceStationCode.setText(cancelledTrains.get(position).getmSourceCode());
            holder.destinationStationName.setText(cancelledTrains.get(position).getmDestinationName());
            holder.destinationStationCode.setText(cancelledTrains.get(position).getmDestinationCode());
            holder.time.setText(cancelledTrains.get(position).getmTime());
            holder.type.setText(cancelledTrains.get(position).getmType());
            holder.trainName.setText(cancelledTrains.get(position).getmName());
            holder.trainNumber.setText(cancelledTrains.get(position).getmNumber());
        }

        @Override
        public int getItemCount() {
            return cancelledTrains.size();
        }

        class CancelledTrainRecylerViewHolder extends RecyclerView.ViewHolder {
            TextView sourceStationName;
            TextView sourceStationCode;
            TextView destinationStationName;
            TextView destinationStationCode;
            TextView time;
            TextView type;
            TextView trainName;
            TextView trainNumber;

            public CancelledTrainRecylerViewHolder(View itemView) {
                super(itemView);
                sourceStationName = itemView.findViewById(R.id.sourcestationname);
                sourceStationCode = itemView.findViewById(R.id.sourcestationcode);
                destinationStationCode = itemView.findViewById(R.id.destinationstationcode);
                destinationStationName = itemView.findViewById(R.id.destinationstationname);
                time = itemView.findViewById(R.id.time);
                type = itemView.findViewById(R.id.type);
                trainName = itemView.findViewById(R.id.trainName);
                trainNumber = itemView.findViewById(R.id.trainNumber);
            }
        }


    }

    public class CancelledTrains {

        String mName;
        String mType;
        String mNumber;
        String mTime;
        String mSourceName;
        String mSourceCode;
        String mDestinationName;
        String mDestinationCode;

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getmType() {
            return mType;
        }

        public void setmType(String mType) {
            this.mType = mType;
        }

        public String getmNumber() {
            return mNumber;
        }

        public void setmNumber(String mNumber) {
            this.mNumber = mNumber;
        }

        public String getmTime() {
            return mTime;
        }

        public void setmTime(String mTime) {
            this.mTime = mTime;
        }

        public String getmSourceName() {
            return mSourceName;
        }

        public void setmSourceName(String mSourceName) {
            this.mSourceName = mSourceName;
        }

        public String getmSourceCode() {
            return mSourceCode;
        }

        public void setmSourceCode(String mSourceCode) {
            this.mSourceCode = mSourceCode;
        }

        public String getmDestinationName() {
            return mDestinationName;
        }

        public void setmDestinationName(String mDestinationName) {
            this.mDestinationName = mDestinationName;
        }

        public String getmDestinationCode() {
            return mDestinationCode;
        }

        public void setmDestinationCode(String mDestinationCode) {
            this.mDestinationCode = mDestinationCode;
        }


    }
}
