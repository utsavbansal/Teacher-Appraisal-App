package utsavbansal.teacher_evaluation_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar)findViewById(R.id.progessbar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=0;i<=100;i+=10)
                    {
                        Thread.sleep(500);

                        progressBar.setProgress(i);
                    }
                    Thread.sleep(1000);
                    startActivity(new Intent(MainActivity.this,HomePage.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
