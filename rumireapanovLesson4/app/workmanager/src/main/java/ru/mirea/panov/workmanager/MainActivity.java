package ru.mirea.panov.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isNetworkAvailable()) {
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED)
                    .setRequiresCharging(true)
                    .build();
            WorkRequest uploadWorkRequest =
                    new OneTimeWorkRequest.Builder(UploadWorker.class)
                            .setConstraints(constraints)
                            .build();
            WorkManager.getInstance(this).enqueue(uploadWorkRequest);
            Log.d(TAG, "WorkManager: Интернет присутствует");
        } else {
            Log.d(TAG, "WorkManager: Нет подключения к интернету");
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
