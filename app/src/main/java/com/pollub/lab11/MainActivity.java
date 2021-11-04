package com.pollub.lab11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonPress(View view){
        if (validate()) {
            getIPInfo();
        }
    }
    private String getIP() {
        EditText e1 = (EditText) findViewById(R.id.ip1);
        EditText e2 = (EditText) findViewById(R.id.ip2);
        EditText e3 = (EditText) findViewById(R.id.ip3);
        EditText e4 = (EditText) findViewById(R.id.ip4);
        String ip = e1.getText().toString() + "." + e2.getText().toString() + "." +
                e3.getText().toString() + "." + e4.getText().toString();
        return ip;
    }
    private void printInfo(IPInfo info) {
        TextView textView = (TextView) findViewById(R.id.textView);
        String s;
        if(info == null) s = "Failed to get info";
        else {
            s = "ip: " + info.getIp() + "\n" +
                    "hostname: " + info.getHostname() + "\n" +
                    "city: " + info.getCity();
        }
        textView.setText(s);
    }
    private void getIPInfo(){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class, getIP());
        Call<IPInfo> call = apiInterface.getIPInfo();
        call.enqueue(new Callback<IPInfo>() {
            @Override
            public void onResponse(Call<IPInfo> call, Response<IPInfo> response) {
                printInfo(response.body());
            }
            @Override
            public void onFailure(Call<IPInfo> call, Throwable t) {
                printInfo(null);
            }
        });
    }

    private boolean validate() {
        EditText e1 = (EditText) findViewById(R.id.ip1);
        EditText e2 = (EditText) findViewById(R.id.ip2);
        EditText e3 = (EditText) findViewById(R.id.ip3);
        EditText e4 = (EditText) findViewById(R.id.ip4);

        if (e1.length() == 0) {
            e1.setError("input cannot be null");
            return false;
        }
        if (e2.length() == 0) {
            e2.setError("input cannot be null");
            return false;
        }
        if (e3.length() == 0) {
            e3.setError("input cannot be null");
            return false;
        }
        if (e4.length() == 0) {
            e4.setError("input cannot be null");
            return false;
        }

        int e1Value = Integer.parseInt(e1.getText().toString());
        int e2Value = Integer.parseInt(e2.getText().toString());
        int e3Value = Integer.parseInt(e3.getText().toString());
        int e4Value = Integer.parseInt(e4.getText().toString());



        if (e1Value < 0 || e1Value > 255) {
            e1.setError("Not valid ip number");
            return false;
        }

        if (e2Value < 0 || e2Value > 255) {
            e2.setError("Not valid ip number");
            return false;
        }
        if (e3Value < 0 || e3Value > 255) {
            e3.setError("Not valid ip number");
            return false;
        }
        if (e4Value < 0 || e4Value > 255) {
            e4.setError("Not valid ip number");
            return false;
        }

        return true;

    }
}