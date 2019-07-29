package in.tiqs.kaushikdhwaneeuser.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.tiqs.kaushikdhwaneeuser.R;

/**
 * Created by TechIq on 2/27/2017.
 */

public class AboutApp extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_app);
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView title=(TextView)findViewById(R.id.ab2_title);
        title.setText(getString(R.string.a_app));
    }
}
