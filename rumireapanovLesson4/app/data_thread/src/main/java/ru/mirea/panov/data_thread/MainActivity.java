package ru.mirea.panov.data_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import ru.mirea.panov.data_thread.databinding.ActivityMainBinding;

//Ответы на вопросы
//runOnUiThread:
//   Метод runOnUiThread позволяет выполнить обновление UI из другого потока.
//   Запускает переданное в него действие (Runnable) в потоке пользовательского интерфейса.
//postDelayed:
//   Метод postDelayed позволяет выполнить действие (Runnable) через определенное время с задержкой.
//   Действие будет выполнено в потоке пользовательского интерфейса.
//   Позволяет установить задержку перед выполнением действия.
//post:
//   Метод post позволяет добавить действие (Runnable) в очередь сообщений для выполнения в потоке, к которому привязан данный объект View.
//   Действие будет выполнено в потоке пользовательского интерфейса.
//   Не имеет задержки.
//   оследовательность запуска:
//
//        Сначала выполняется действие метода runOnUiThread.
//        После этого выполняется действие метода postDelayed с задержкой.
//        Затем выполняется действие метода post без задержки.
//        Относительно элемента TextView:
//
//   android:maxLines="10" устанавливает максимальное количество строк, которое TextView может отображать.
//   android:lines="10" устанавливает количество строк текста в TextView.
//   Таким образом, различие между ними заключается в том, что android:maxLines="10" ограничивает количество строк, которые могут быть отображены, в то время как android:lines="10" устанавливает количество строк текста, которые будут отображены, независимо от их количества.

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        final Runnable runn1 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn1");
            }
        };
        final Runnable runn2 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn2");
            }
        };
        final Runnable runn3 = new Runnable() {
            public void run() {
                binding.tvInfo.setText("runn3");
            }
        };
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);
                    TimeUnit.SECONDS.sleep(1);
                    binding.tvInfo.postDelayed(runn3, 2000);
                    binding.tvInfo.post(runn2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}