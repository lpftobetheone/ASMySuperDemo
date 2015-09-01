package com.lpf.mysuperdemo.daojishi;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class Daojishiservice extends Service {


    Timer timer = null;
    TimerTask task = null;

    private int currentNum = 10;

    public Daojishiservice() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("on bind");
        return binder;
    }

    private final MyBinder binder = new MyBinder();

    public class MyBinder extends Binder
    {
        public Daojishiservice getService() {

            return Daojishiservice.this;
        }
    }

    @Override
    public void onCreate() {
        System.out.println("service start");
        startTimer();

        super.onCreate();

    }

    @Override
    public void onDestroy() {
        System.out.println("service destory");
        stopTimer();
        super.onDestroy();
    }



    public void startTimer(){
        if(timer==null){
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    currentNum--;
                    System.out.println(currentNum);
                }
            };

            timer.schedule(task,1000,1000);
        }
    }

    public void stopTimer(){
        if(timer!=null){
            timer.cancel();
            task.cancel();
            task = null;
            timer = null;
        }
    }

    public int getCurrentNumber(){
        return currentNum;
    }

}
