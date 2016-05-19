package com.example.questdot.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.questdot.aidl_sbserviceexample.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private Intent serviceIntent;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);

        serviceIntent= new Intent();
        serviceIntent.setComponent(new ComponentName("com.example.questdot.aidl_sbserviceexample","com.example.questdot.aidl_sbserviceexample.MyService"));


        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.btnSyn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnStart:


                startService(serviceIntent);
                break;

            case R.id.btnStop:
                stopService(serviceIntent);
                break;
            case R.id.button:


                bindService(serviceIntent,this, Context.BIND_AUTO_CREATE);
                break;

            case R.id.button2:
                unbindService(this);
                binder=null;
                break;


            case R.id.btnSyn:
                if(binder!=null){
                    try {
                        binder.setData(editText.getText().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        System.out.println("Connected"+service);
        binder = IMyAidlInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
    private IMyAidlInterface binder = null;
}
