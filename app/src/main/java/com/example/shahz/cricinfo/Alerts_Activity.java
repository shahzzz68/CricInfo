package com.example.shahz.cricinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Alerts_Activity extends AppCompatActivity {

    TextView notyTitle, notyBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts_);

        Intent intent=getIntent();

        if (intent!=null)
        {
            String notiTitle = intent.getStringExtra("messageTitl");
            String notiBody = intent.getStringExtra("messageBod");

            notyTitle =  findViewById(R.id.alertstitle);
            notyBody =findViewById(R.id.alertsBody);

            notyTitle.setText(notiTitle);
            notyBody.setText(notiBody);
        }
        else
        {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
