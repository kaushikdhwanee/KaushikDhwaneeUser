package in.tiqs.kaushikdhwaneeuser.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.tiqs.kaushikdhwaneeuser.R;

/**
 * Created by TechIq on 3/16/2017.
 */

public class Pg extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pg);
        TextView title=(TextView)findViewById(R.id.ab2_title);
        title.setText("Payment Gateway");
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
