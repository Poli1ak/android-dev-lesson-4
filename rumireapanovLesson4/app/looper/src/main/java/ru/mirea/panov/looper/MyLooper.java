package ru.mirea.panov.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MyLooper extends Thread {
    public Handler mHandler;
    private Handler mainHandler;

    public MyLooper(Handler mainThreadHandler) {
        mainHandler = mainThreadHandler;
    }

    public void run() {
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message msg) {
                int age = msg.getData().getInt("age");
                String work = msg.getData().getString("work");

                try {
                    Thread.sleep(age * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String result = String.format("Ваш возраст %d и вы работаете %s.", age, work);

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                message.setData(bundle);
                mainHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }
}

