package in.tiqs.kaushikdhwaneeuser.act;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import in.tiqs.kaushikdhwaneeuser.R;

/**
 * Created by TechIq on 2/24/2017.
 */

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_updated);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

               // new ScaleInAnimation(logo).setDuration(500).animate();
                startActivity(new Intent(Splash.this,MainActivity.class));
                finish();

            }
        }, 3000);
    }
}
