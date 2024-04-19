package ru.mirea.panov.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ru.mirea.panov.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TextView infoTextView;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        infoTextView = binding.infoTextview;

        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Имя текущего потока: " + mainThread.getName());
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: БСБО-09-21, НОМЕР ПО СПИСКУ: 18, МОЙ ЛЮБИМЫЙ ФИЛЬМ: КОЛОБОК");
        infoTextView.append("\n Новое имя потока: " + mainThread.getName());

        binding.calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        int numberThread = counter++;
                        Log.d("ThreadProject", String.format("Запущен поток № %d студентом группы № %s номер по списку № %d", numberThread, "БСБО-09-21", 18));
                        long endTime = System.currentTimeMillis() + 20 * 1000;
                        while (System.currentTimeMillis() < endTime) {
                            synchronized (this) {
                                try {
                                    wait(endTime - System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(), "Endtime: " + endTime);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            Log.d("ThreadProject", "Выполнен поток № " + numberThread);
                        }
                    }
                }).start();
                calculateAveragePairs();
            }
        });
    }

    private void calculateAveragePairs() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int totalPairs = Integer.parseInt(binding.totalPairsEdittext.getText().toString());
                int totalDays = Integer.parseInt(binding.totalDaysEdittext.getText().toString());

                final double averagePairsPerDay = totalPairs / (double) totalDays;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.resultTextview.setText("Среднее количество пар в день: " + averagePairsPerDay);
                    }
                });
            }
        }).start();
    }
}
