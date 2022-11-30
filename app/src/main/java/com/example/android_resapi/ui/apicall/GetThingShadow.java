package com.example.android_resapi.ui.apicall;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.example.android_resapi.R;
import com.example.android_resapi.httpconnection.GetRequest;

public class GetThingShadow extends GetRequest {
    final static String TAG = "AndroidAPITest";
    String urlStr;
    public GetThingShadow(Activity activity, String urlStr) {
        super(activity);
        this.urlStr = urlStr;
    }

    @Override
    protected void onPreExecute() {
        try {
            Log.e(TAG, urlStr);
            url = new URL(urlStr);

        } catch (MalformedURLException e) {
            Toast.makeText(activity,"URL is invalid:"+urlStr, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            activity.finish();
        }
    }

    @Override
    protected void onPostExecute(String jsonString) {
        if (jsonString == null)
            return;
        Map<String, String> state = getStateFromJSONString(jsonString);
        TextView reported_waterLevelTV = activity.findViewById(R.id.reported_waterLevel);
        TextView reported_pirStateTV = activity.findViewById(R.id.reported_pirState);
        TextView reported_ledTV = activity.findViewById(R.id.reported_led);
        TextView reported_motionTV = activity.findViewById(R.id.reported_motion);
        reported_motionTV.setText(state.get("reported_motion"));
        reported_ledTV.setText(state.get("reported_LED"));
        reported_pirStateTV.setText(state.get("reported_pirState"));
        reported_waterLevelTV.setText(state.get("reported_waterLevel"));

        TextView desired_waterLevelTV =activity.findViewById(R.id.desired_waterLevel);
        TextView desired_pirStateTV = activity.findViewById(R.id.desired_pirState);
        TextView desired_ledTV = activity.findViewById(R.id.desired_led);
        TextView desired_motionTV = activity.findViewById(R.id.desired_motion);
        desired_motionTV.setText(state.get("desired_motion"));
        desired_ledTV.setText(state.get("desired_LED"));
        desired_pirStateTV.setText(state.get("desired_pirState"));
        desired_waterLevelTV.setText(state.get("desired_waterLevel"));

    }

    protected Map<String, String> getStateFromJSONString(String jsonString) {
        Map<String, String> output = new HashMap<>();
        try {
            // 처음 double-quote와 마지막 double-quote 제거
            jsonString = jsonString.substring(1,jsonString.length()-1);
            // \\\" 를 \"로 치환
            jsonString = jsonString.replace("\\\"","\"");
            Log.i(TAG, "jsonString="+jsonString);
            JSONObject root = new JSONObject(jsonString);
            JSONObject state = root.getJSONObject("state");
            JSONObject reported = state.getJSONObject("reported");
            String waterLevelValue = reported.getString("Water_Level");
            String pirStateValue = reported.getString("pirState");
            String ledValue = reported.getString("LED");
            String motionValue = reported.getString("Motion");
            output.put("reported_waterLevel",waterLevelValue);
            output.put("reported_pirState",pirStateValue);
            output.put("reported_LED",ledValue);
            output.put("reported_motion",motionValue);

            JSONObject desired = state.getJSONObject("desired");
            String desired_waterLevelValue = desired.getString("Water_Level");
            String desired_pirStateValue = desired.getString("pirState");
            String desired_ledValue = desired.getString("LED");
            String desired_motionValue = desired.getString("Motion");
            output.put("desired_waterLevel",desired_waterLevelValue);
            output.put("desired_pirState",desired_pirStateValue);
            output.put("desired_LED",desired_ledValue);
            output.put("desired_motion",desired_motionValue);

        } catch (JSONException e) {
            Log.e(TAG, "Exception in processing JSONString.", e);
            e.printStackTrace();
        }
        return output;
    }
}
