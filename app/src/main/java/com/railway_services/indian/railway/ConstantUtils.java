package com.railway_services.indian.railway;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Abhishek on 04-03-2018.
 */

public class ConstantUtils {

    Context mCtx;
    public static final String API_KEY = "860z36j8dm";

    private static String PNR_URL = "https://api.railwayapi.com/v2/pnr-status/pnr";
    private static String TRAIN_ROUTE = "https://api.railwayapi.com/v2/route/train";

    private static String SEAT_AVAILABILITY = "https://api.railwayapi.com/v2/check-seat/train";
    private static final String TRAIN_BETWEN_STATION = "https://api.railwayapi.com/v2/between/source";

    private static String LIVE_TRAIN_STATUS = "https://api.railwayapi.com/v2/live/train";
    private static String FARE_ENQUIRY = "https://api.railwayapi.com/v2/fare/train";

    ConstantUtils(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static String getFareEnquiry(String train_number, String sstation, String dstation, String age, String seat_type, String quota, String date) {

        String constructed_url = "";
        if (!TextUtils.isEmpty(train_number) && !TextUtils.isEmpty(quota) && !TextUtils.isEmpty(sstation) && !TextUtils.isEmpty(dstation) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(seat_type)) {
            Uri uri = Uri.parse(FARE_ENQUIRY).buildUpon()
                    .appendEncodedPath(train_number)
                    .appendEncodedPath("source")
                    .appendEncodedPath(sstation)
                    .appendEncodedPath("dest")
                    .appendEncodedPath(dstation)
                    .appendEncodedPath("age")
                    .appendEncodedPath(age)
                    .appendEncodedPath("pref")
                    .appendEncodedPath(seat_type)
                    .appendEncodedPath("quota")
                    .appendEncodedPath(quota)
                    .appendEncodedPath("date")
                    .appendEncodedPath(date)
                    .appendEncodedPath("apikey")
                    .appendPath(ConstantUtils.API_KEY).build();
            constructed_url = uri.toString();
            Log.i("SEAT", constructed_url);
        }
        return constructed_url;

    }

    public static String buildSeatAvailabilityUrl(String train_number, String sstation, String dstation, String date, String seat_type, String quota) {

        String constructed_url = "";
        if (!TextUtils.isEmpty(train_number) && !TextUtils.isEmpty(quota) && !TextUtils.isEmpty(sstation) && !TextUtils.isEmpty(dstation) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(seat_type)) {
            Uri uri = Uri.parse(SEAT_AVAILABILITY).buildUpon()
                    .appendEncodedPath(train_number)
                    .appendEncodedPath("source")
                    .appendEncodedPath(sstation)
                    .appendEncodedPath("dest")
                    .appendEncodedPath(dstation)
                    .appendEncodedPath("date")
                    .appendEncodedPath(date)
                    .appendEncodedPath("pref")
                    .appendEncodedPath(seat_type)
                    .appendEncodedPath("quota")
                    .appendEncodedPath(quota)
                    .appendEncodedPath("apikey")
                    .appendPath(ConstantUtils.API_KEY).build();
            constructed_url = uri.toString();
            Log.i("SEAT", constructed_url);
        }
        return constructed_url;

    }

    public static String builPnrurl(Context ctx,String pnr_number) {
        String constructed_url = "";
        if (TextUtils.isEmpty(pnr_number)) {
            Toast.makeText(ctx, "Enter valid PNR number", Toast.LENGTH_LONG).show();
            return "";
        } else {

            Uri uri = Uri.parse(PNR_URL).buildUpon().appendPath(pnr_number).appendEncodedPath("apikey").appendPath(ConstantUtils.API_KEY).build();
            constructed_url = uri.toString();
        }
        return constructed_url;
    }

    public static String buildLiveTrainUrl(String train_number, String date) {
        if (!TextUtils.isEmpty(train_number) && !TextUtils.isEmpty(date)) {
            Uri uri = Uri.parse(LIVE_TRAIN_STATUS).buildUpon().appendPath(train_number).appendEncodedPath("date")
                    .appendEncodedPath(date).appendEncodedPath("apikey")
                    .appendPath(ConstantUtils.API_KEY).build();
            Log.i("LIVE", uri.toString());
            return uri.toString();
        } else {
            return null;
        }

    }

    public static String buildRailRoute(String train_number) {
        if (!TextUtils.isEmpty(train_number)) {
            Uri uri = Uri.parse(TRAIN_ROUTE).buildUpon().appendPath(train_number)
                    .appendEncodedPath("apikey")
                    .appendPath(ConstantUtils.API_KEY).build();
            Log.i("LIVE", uri.toString());
            return uri.toString();
        } else {
            return null;
        }

    }


    public  String trainsBetwenStaion(String sstaion, String dstation, String date) {
        if (!TextUtils.isEmpty(sstaion) && !TextUtils.isEmpty(dstation) && !TextUtils.isEmpty(date)) {
            Uri uri = Uri.parse(TRAIN_BETWEN_STATION).buildUpon()
                    .appendPath(sstaion)
                    .appendEncodedPath("dest")
                    .appendEncodedPath(dstation)
                    .appendEncodedPath("date")
                    .appendEncodedPath(date)
                    .appendEncodedPath("apikey")
                    .appendPath(ConstantUtils.API_KEY).build();
            Log.i("Between", uri.toString());
            return uri.toString();
        } else {
            return null;
        }

    }


    public static final String RESOPNSE_CODE="response_code";
    public static final String TRAINS="trains";
    public static final String DAYS="days";
    public static final String CLASSES="classes";
    public static final String TOTAL="total";


}
