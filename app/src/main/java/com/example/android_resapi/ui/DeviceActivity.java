package com.example.android_resapi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android_resapi.R;
import com.example.android_resapi.ui.apicall.GetThingShadow;
import com.example.android_resapi.ui.apicall.UpdateShadow;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Timer;
import java.util.TimerTask;

public class DeviceActivity extends AppCompatActivity {
    String urlStr;
    final static String TAG = "AndroidAPITest";
    Timer timer;
    Button startGetBtn;
    Button stopGetBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        Intent intent = getIntent();
        urlStr = intent.getStringExtra("thingShadowURL");

        startGetBtn = findViewById(R.id.startGetBtn);
        startGetBtn.setEnabled(true);
        startGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        new GetThingShadow(DeviceActivity.this, urlStr).execute();
                    }
                },
                        0,2000);

                startGetBtn.setEnabled(false);
                stopGetBtn.setEnabled(true);
            }
        });

        stopGetBtn = findViewById(R.id.stopGetBtn);
        stopGetBtn.setEnabled(false);
        stopGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null)
                    timer.cancel();
                clearTextView();
                startGetBtn.setEnabled(true);
                stopGetBtn.setEnabled(false);
            }
        });

        Button updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit_std = findViewById(R.id.edit_std);

                JSONObject payload = new JSONObject();

                try {
                    JSONArray jsonArray = new JSONArray();
                    String temp_input = edit_std.getText().toString();
                    if (temp_input != null && !temp_input.equals("")) {
                        JSONObject tag1 = new JSONObject();
                        tag1.put("tagName", "std");
                        tag1.put("tagValue", temp_input);

                        jsonArray.put(tag1);
                    }

                    if (jsonArray.length() > 0)
                        payload.put("tags", jsonArray);
                } catch (JSONException e) {
                    Log.e(TAG, "JSONEXception");
                }
                Log.i(TAG,"payload="+payload);
                if (payload.length() >0 )
                    new UpdateShadow(DeviceActivity.this,urlStr).execute(payload);
                else
                    Toast.makeText(DeviceActivity.this,"변경할 상태 정보 입력이 필요합니다", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void clearTextView() {
        TextView reported_waterLevelTV = findViewById(R.id.reported_waterLevel);
        TextView reported_waterStateTV = findViewById(R.id.reported_waterState);
        TextView reported_pirStateTV = findViewById(R.id.reported_pirState);
        TextView reported_ledTV = findViewById(R.id.reported_led);
        TextView reported_motionTV = findViewById(R.id.reported_motion);
        reported_ledTV.setText("");
        reported_pirStateTV.setText("");
        reported_waterStateTV.setText("");
        reported_waterLevelTV.setText("");
        reported_motionTV.setText("");

        TextView desired_waterLevelTV =findViewById(R.id.desired_waterLevel);
        TextView desired_waterStateTV = findViewById(R.id.desired_waterState);
        TextView desired_pirStateTV = findViewById(R.id.desired_pirState);
        TextView desired_ledTV = findViewById(R.id.desired_led);
        TextView desired_motionTV = findViewById(R.id.desired_motion);
        desired_ledTV.setText("");
        desired_pirStateTV.setText("");
        desired_waterStateTV.setText("");
        desired_waterLevelTV.setText("");
        desired_motionTV.setText("");
    }

}


