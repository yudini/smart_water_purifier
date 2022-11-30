package com.example.android_resapi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android_resapi.R;


public class MainActivity extends AppCompatActivity {
    final static String TAG = "AndroidAPITest";
    EditText listThingsURL, getShadowURL,updateShadowURL, getLogsURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listThingsURL = findViewById(R.id.listThingsURL);
        getShadowURL = findViewById(R.id.getShadowURL);
        updateShadowURL = findViewById(R.id.updateShadowURL);
        getLogsURL = findViewById(R.id.getLogsURL);

        Button listThingsBtn = findViewById(R.id.listThingsBtn);
        listThingsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String urlstr = listThingsURL.getText().toString();
                Log.i(TAG, "listThingsURL=" + urlstr);
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(MainActivity.this, "사물목록 조회 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, ListThingsActivity.class);
                intent.putExtra("listThingsURL", listThingsURL.getText().toString());
                startActivity(intent);
                //  new GetThings(MainActivity.this).execute();
                //  new GetThingShadow(MainActivity.this, "MyMKRWiFi1010").execute();

            }
        });

        Button getShadowBtn = findViewById(R.id.getShadowBtn);
        getShadowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlstr = getShadowURL.getText().toString();
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(MainActivity.this, "사물상태 조회 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, DeviceActivity.class);
                intent.putExtra("getShadowURL", getShadowURL.getText().toString());
                startActivity(intent);

            }
        });

        Button updateShadowBtn = findViewById(R.id.updateShadowBtn);
        updateShadowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlstr = updateShadowURL.getText().toString();
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(MainActivity.this, "사물상태 변경 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("updateShadowURL", updateShadowURL.getText().toString());
                startActivity(intent);

            }
        });

        Button listLogsBtn = findViewById(R.id.listLogsBtn);
        listLogsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlstr = getLogsURL.getText().toString();
                if (urlstr == null || urlstr.equals("")) {
                    Toast.makeText(MainActivity.this, "사물로그 조회 API URI 입력이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                intent.putExtra("getLogsURL", getLogsURL.getText().toString());
                startActivity(intent);
            }
        });
    }
}


